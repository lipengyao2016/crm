package com.crm.assistant.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.crm.utils.DateTimeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;

import static com.crm.utils.RandUtil.*;
import static com.crm.utils.StringUtil.*;

@Component
public class FileUtil {

	public static final String TYPE_IMG = "jpg";

	public static final String TYPE_ZIP = "zip";

	public static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	private static final String FFMPEG_PATH = "/root/ffmpeg-4.0.1/";
	/**
	 * 阿里云存储公司环境
	 */
	@Value("${attachment.bucketName}")
	private String bucketName;
	@Value("${attachment.secretId}")
	private String accessKeyId_boyin;
	@Value("${attachment.secretKey}")
	private String secretAccessKey_boyin;
	@Value("${attachment.endpoint}")
	private String endpoint;

	/**
	 * 上传的图片转化为JPG格式 JDK1.6有BUG,读取部分PNG图片时会出错
	 */
	public static String toJPG(File srcFile, File targetFile) {
		try {
			BufferedImage srcImage = ImageIO.read(srcFile);
			BufferedImage bi = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.getGraphics();
			g.drawImage(srcImage, 0, 0, null);
			g.dispose();
			ImageIO.write(bi, TYPE_IMG, targetFile);
			deleteFile(srcFile);
			return targetFile.getName();
		} catch (Exception e) {
			// errorlog(e);
			// BufferedInputStream is=new BufferedInputStream(new FileInputStream(srcFile));
			// BufferedOutputStream os=new BufferedOutputStream(new
			// FileOutputStream(targetFile));
			// int read=0;
			// while((read=is.read())!=-1){
			// os.write(read);
			// }
			// is.close();
			// os.close();
			return srcFile.getName();
		}
	}

	public static List<File> listDirs(File f) {
		List<File> list = new ArrayList<File>();
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			list.add(f);
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					list.addAll(listDirs(fs[i]));
				}
			}
		}
		return list;
	}

	public static List<File> listFiles(File f) {
		List<File> list = new ArrayList<File>();
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			if (fs != null) {
				list.addAll(Arrays.asList(fs));
			}
		}
		return list;
	}

	public static void deleteFile(File f) {
		if (f.isDirectory()) {
			File[] cs = f.listFiles();
			for (File child : cs) {
				deleteFile(child);
			}
		}
		int rcount = 0;
		while (f.exists() && rcount < 10) {
			System.gc();
			f.delete();
			rcount++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("{}", e);
			}
		}
	}
	

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					continue;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 获取amr文件时长
	 */
	public static long getAmrDuration(File f) throws Exception {
		long duration = -1;
		int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0 };
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(f, "rw");
			long length = f.length();// 文件的长度
			int pos = 6;// 设置初始位置
			int frameCount = 0;// 初始帧数
			int packedPos = -1;
			byte[] datas = new byte[1];// 初始数据值
			while (pos <= length) {
				randomAccessFile.seek(pos);
				if (randomAccessFile.read(datas, 0, 1) != 1) {
					duration = length > 0 ? ((length - 6) / 650) : 0;
					break;
				}
				packedPos = (datas[0] >> 3) & 0x0F;
				pos += packedSize[packedPos] + 1;
				frameCount++;
			}
			duration += frameCount * 20;// 帧数*20
		} finally {
			if (randomAccessFile != null) {
				randomAccessFile.close();
			}
		}
		return duration;
	}

	static String realpath = FileUtil.class.getResource("/").getFile().replace("WEB-INF/classes/", "");
	static String ffmpegPath = realpath + "ffmpeg/";
	static {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os.indexOf("Windows") != -1) {
			ffmpegPath = ffmpegPath.substring(1) + "ffmpeg.exe";
		}
		if (os.indexOf("Linux") != -1) {
			ffmpegPath += "./ffmpeg";
		}
	}

	/**
	 * 获取媒体文件时长(毫秒)
	 */
	public static long getMediaDuration(File file) throws Exception {
		long duration = 0L;// 时长,以毫秒为单位
		List<String> command = new ArrayList<String>();
		command.add(ffmpegPath);
		command.add("-i");
		command.add(file.getPath());
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(command);
		pb.redirectErrorStream(true);
		Process p = pb.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		Pattern pattern = Pattern.compile("Duration: ([0-9]{2}):([0-9]{2}):([0-9]{2}\\.[0-9]*)");
		while ((line = br.readLine()) != null) {
			Matcher m = pattern.matcher(line);
			if (m.find()) {
				float hour = Float.parseFloat(m.group(1));
				float minute = Float.parseFloat(m.group(2));
				float second = Float.parseFloat(m.group(3));
				duration = (long) ((hour * 3600f + minute * 60f + second) * 1000f);
				break;
			}
		}
		br.close();
		p.waitFor();
		return duration;
	}

	/**
	 * 下载文件
	 */
	public static File downloadFile(String url, String type) {
		String realpath = FileUtil.class.getResource("/").getFile().replace("WEB-INF/classes/", "");
		String filename = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date()) + randInt(100, 999);
		if (type.equals("image")) {
			filename += ".jpg";
		}
		if (type.equals("voice")) {
			filename += ".amr";
		}
		if (type.equals("video")) {
			filename += ".mp4";
		}
		File downloadFile = new File(realpath + "download/" + filename);
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = HttpClients.createDefault().execute(get);
			BufferedInputStream is = new BufferedInputStream(response.getEntity().getContent());
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(downloadFile));
			int read = -1;
			while ((read = is.read()) != -1) {
				os.write(read);
			}
			is.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			log.error("{}", e);
			return null;
		}
		return downloadFile;
	}

	/**
	 * 把以字节为单位的文件长度转化为便于阅读的格式(KB,MB,GB)
	 */
	public static String formatFileLength(long fileLength) {
		long kb = 1024L;
		long mb = kb * 1024L;
		long gb = mb * 1024L;
		BigDecimal n1 = new BigDecimal(fileLength);
		BigDecimal n2 = null;
		String suffix = "";
		if (fileLength < kb) {
			return fileLength + "B";
		} else if (fileLength > kb && fileLength < mb) {
			n2 = new BigDecimal(kb);
			suffix = "KB";
		} else if (fileLength > mb && fileLength < gb) {
			n2 = new BigDecimal(mb);
			suffix = "MB";
		} else {
			n2 = new BigDecimal(gb);
			suffix = "GB";
		}
		// 保留两位有效数字,四舍五入
		BigDecimal n3 = n1.divide(n2).setScale(2, BigDecimal.ROUND_HALF_UP);
		return n3.toString() + suffix;
	}

	/**
	 * 把以毫秒为单位的视频时长转化为便于阅读的格式(XX(分):XX(秒))
	 */
	public static String formatVideoDuration(long fileLength) {
		int s = (int) (fileLength / 1000L);
		return getNo(s / 60, 2) + ":" + getNo(s % 60, 2);
	}

	/**
	 * 获取图片宽高
	 */
	public static int[] getImageWH(File f) {
		try {
			BufferedImage im = ImageIO.read(f);
			return new int[] { im.getWidth(), im.getHeight() };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取文件MD5
	 */
	public static String getFileMD5(File f) {
		try {
			FileInputStream is = new FileInputStream(f);
			String md5 = DigestUtils.md5Hex(is);
			is.close();
			return md5;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AMR转AAC
	 */
	// public static File amrToAac(File src) throws Exception{
	// String filename=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new
	// Date())+randInt(100, 999);
	// File target=new File(realpath+"download/"+filename+".aac");
	// List<String> command=new ArrayList<String>();
	// command.add(ffmpegPath);
	// command.add("-i");
	// command.add(src.getPath());
	// command.add("-ab");
	// command.add("26k");
	// command.add("-ar");
	// command.add("22050");
	// command.add(target.getPath());
	// ProcessBuilder pb=new ProcessBuilder();
	// pb.command(command);
	// pb.redirectErrorStream(true);
	// Process p= pb.start();
	// BufferedReader br=new BufferedReader(new
	// InputStreamReader(p.getInputStream()));
	// String line=null;
	// while((line=br.readLine())!=null){
	// System.out.println(line);
	// }
	// br.close();
	// p.waitFor();
	// return target;
	// }

	/**
	 * AMR转AAC
	 */
	// public static File aacToAmr(File src) throws Exception{
	// String filename=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new
	// Date())+randInt(100, 999);
	// File target=new File(realpath+"download/"+filename+".amr");
	// List<String> command=new ArrayList<String>();
	// command.add(ffmpegPath);
	// command.add("-i");
	// command.add(src.getPath());
	// command.add("-ab");
	//// command.add("4.75k");
	//// command.add("5.15k");
	// command.add("5.9k");
	//// command.add("6.7k");
	//// command.add("7.4k");
	//// command.add("7.95k");
	//// command.add("10.2k");
	//// command.add("12.2k");
	// command.add("-ar");
	// command.add("8000");
	// command.add(target.getPath());
	// ProcessBuilder pb=new ProcessBuilder();
	// pb.command(command);
	// pb.redirectErrorStream(true);
	// Process p= pb.start();
	// BufferedReader br=new BufferedReader(new
	// InputStreamReader(p.getInputStream()));
	// String line=null;
	// while((line=br.readLine())!=null){
	// System.out.println(line);
	// }
	// br.close();
	// p.waitFor();
	// return target;
	// }

	/**
	 * 视频截图
	 */
	public static File getVideoThumb(File video) throws Exception {
		String filename = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date()) + randInt(100, 999);
		File thumb = new File(realpath + "download/" + filename + ".jpg");
		List<String> command = new ArrayList<String>();
		command.add(ffmpegPath);
		command.add("-ss");
		command.add("00:00:01");
		command.add("-i");
		command.add(video.getPath());
		command.add("-f");
		command.add("image2");
		command.add("-y");
		command.add(thumb.getPath());
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(command);
		pb.redirectErrorStream(true);
		Process p = pb.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		br.close();
		p.waitFor();
		return thumb;
	}

	/**
	 * 上传文件到阿里云,返回url
	 */
	public String aliUpload(File f) throws Throwable {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId_boyin, secretAccessKey_boyin);
		// 上传到阿里云
		String stringDateShort = DateTimeUtil.getStringDateShort();
		String[] split = stringDateShort.split("-");
		String alisavepath = "crm/college/" + split[0] + "/" + split[1] + "/" + split[2] + "/java_" + f.getName();
		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, alisavepath);
		// 待上传的本地文件
		uploadFileRequest.setUploadFile(f.getAbsolutePath());
		// 设置并发下载数，默认1
		uploadFileRequest.setTaskNum(5);
		// 设置分片大小，默认100KB
		uploadFileRequest.setPartSize(1024 * 1024 * 1);
		// 开启断点续传，默认关闭
		uploadFileRequest.setEnableCheckpoint(true);
		// 默认设置为图片
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType("image/jpeg");
		uploadFileRequest.setObjectMetadata(meta);

		UploadFileResult uploadResult = ossClient.uploadFile(uploadFileRequest);
		CompleteMultipartUploadResult multipartUploadResult = uploadResult.getMultipartUploadResult();
		String url = multipartUploadResult.getLocation();
		return url;
		// return generateAliUrl(url, true);
	}

	/**
	 * 为阿里云存储的url加上授权字符串
	 * 
	 */
	public String generateAliUrl(String url, boolean fromPC) throws Exception {
		/*if (isEmpty(url)) {
			log.info("文件路径为空");
			return "";
		}
		// 正则校检文件路径
		String pattern1 = "^http://.*";
		String pattern2 = "^http://" + bucketName + "." + endpoint + ".*";
		if (!Pattern.matches(pattern1, url)) {
			url = "http://" + url;
		}
//		if (!Pattern.matches(pattern2, url)) {
//			log.debug("文件路径不正确:'" + url + "'");
//			return "";
//		}
		if (url.toString().indexOf("?") != -1) {
			url = url.substring(0, url.toString().indexOf("?"));
		}
		String accessKeyId = accessKeyId_boyin;
		String secretAccessKey = secretAccessKey_boyin;
		String bucketName = url.substring(url.indexOf("http://") + 7, url.indexOf("."));

		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
		// 文件存放相对路径,即阿里API传入参数的key
		String alisavepath = url.substring(url.indexOf("com/") + 4);
		// 生成带授权字符串的文件访问路径
		long second = fromPC ? 60L : 3600L * 24L * 352L * 10L;
		Date exp = new Date(new Date().getTime() + 1000L * second);
		return ossClient.generatePresignedUrl(bucketName, alisavepath, exp).toString();*/
		return url;
	}

	public static void createFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void mergeFile(List<File> file, String mergePath, String fileName) {
		File f3 = new File(mergePath + fileName + ".aac");

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f3);
			byte b[] = new byte[1024];
			for (File file2 : file) {
				FileInputStream inpu1 = new FileInputStream(file2);

				int len = 0;
				// 把file的内容流到f3中
				while ((len = inpu1.read(b)) != -1) {
					for (int i = 0; i < len; i++) {
						out.write(b[i]);
					}
				}
				inpu1.close();

			}
			out.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("合并完成");
	}

	public static boolean ffmpegTransfer(String infile, String outfile) {
		String avitoflv = FFMPEG_PATH + "./ffmpeg -i " + infile + " -acodec libmp3lame " + outfile;
		try {
			log.info("执行命令:" + avitoflv);
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(avitoflv);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			log.info("<ERROR>");
			while ((line = br.readLine()) != null)
				log.info(line);
			log.info("</ERROR>");
			int exitVal = proc.waitFor();
			log.info("Process exitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
			log.info("执行转换命令出错!" + t.getMessage());
			return false;
		}
		return true;
	}
	// private void executeUpload(String uploadDir,MultipartFile file)throws
	// Exception{
	// //获取后缀名
	// String
	// suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	// //生成上传文件名
	// String filename=UUID.randomUUID()+suffix;
	// //服务器保存的文件对象
	// File serverFile=new File(uploadDir+filename);
	// //将文件写入服务器中
	// file.transferTo(serverFile);
	// }
}
