package com.crm.assistant.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.util.List;

import javax.imageio.ImageIO;

import com.crm.common.MessageException;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;

public class PptToImageUtil {
/*
	*//**
	 * ppt2003 文档的转换 后缀名为.ppt
	 * 
	 * @param pptFile
	 *            ppt文件
	 * @param imgFile
	 *            图片将要保存的目录（不是文件）
	 * @return
	 *//*
	public static Integer doPPT2003toImage(File pptFile, File imgFile, String fileName) {

		try {
			FileInputStream is = new FileInputStream(pptFile);
			SlideShow ppt = new SlideShow(is);

			// 及时关闭掉 输入流
			is.close();

			Dimension pgsize = ppt.getPageSize();
			Slide[] slide = ppt.getSlides();

			for (int i = 0; i < slide.length; i++) {

				System.out.println("第" + i + "页。");

				TextRun[] truns = slide[i].getTextRuns();

				for (int k = 0; k < truns.length; k++) {

					RichTextRun[] rtruns = truns[k].getRichTextRuns();

					for (int l = 0; l < rtruns.length; l++) {

						// 原有的字体索引 和 字体名字
						int index = rtruns[l].getFontIndex();
						String name = rtruns[l].getFontName();

						System.out.println("原有的字体索引 和 字体名字: " + index + " - " + name);

						// 重新设置 字体索引 和 字体名称 是为了防止生成的图片乱码问题
						rtruns[l].setFontIndex(1);
						rtruns[l].setFontName("华文宋体");
					}

				}

				// 根据幻灯片大小生成图片
				BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = img.createGraphics();

				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
				slide[i].draw(graphics);

				// 图片的保存位置
				String absolutePath = imgFile.getAbsolutePath() + "/" + fileName + "_" + (i + 1) + ".jpeg";
				File jpegFile = new File(absolutePath);

				// 如果图片存在，则不再生成
				if (jpegFile.exists()) {
					continue;
				}

				// 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
				FileOutputStream out = new FileOutputStream(jpegFile);

				ImageIO.write(img, "jpeg", out);

				out.close();

			}

			System.out.println("PPT转换成图片 成功！");

			return slide.length;

		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageException("PPT转换成图片失败！");
		}
	}

	*//**
	 * ppt2007文档的转换 后缀为.pptx
	 * 
	 * @param pptFile
	 *            PPT文件
	 * @param imgFile
	 *            图片将要保存的路径目录（不是文件）
	 *            存放文件名的 list
	 * @return
	 *//*
	public static Integer doPPT2007toImage(File pptFile, File imgFile, String fileName) {

		FileInputStream is = null;

		try {

			is = new FileInputStream(pptFile);

			XMLSlideShow xmlSlideShow = new XMLSlideShow(is);

			is.close();

			// 获取大小
			Dimension pgsize = xmlSlideShow.getPageSize();

			// 获取幻灯片
			XSLFSlide[] slides = xmlSlideShow.getSlides();

			for (int i = 0; i < slides.length; i++) {

				// 解决乱码问题
				XSLFShape[] shapes = slides[i].getShapes();
				for (XSLFShape shape : shapes) {

					if (shape instanceof XSLFTextShape) {
						XSLFTextShape sh = (XSLFTextShape) shape;
						List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();

						for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
							List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
							for (XSLFTextRun xslfTextRun : textRuns) {
								xslfTextRun.setFontFamily("华文宋体");
							}
						}
					}
				}

				// 根据幻灯片大小生成图片
				BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = img.createGraphics();

				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

				// 最核心的代码
				slides[i].draw(graphics);

				// 图片将要存放的路径
				String absolutePath = imgFile.getAbsolutePath() + "/" + fileName + "_" + (i + 1) + ".jpeg";
				File jpegFile = new File(absolutePath);

				// 如果图片存在，则不再生成
				if (jpegFile.exists()) {
					continue;
				}
				// 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
				FileOutputStream out = new FileOutputStream(jpegFile);

				// 写入到图片中去
				ImageIO.write(img, "jpeg", out);

				out.close();

			}

			System.out.println("PPT转换成图片 成功！");

			return slides.length;

		} catch (Exception e) {
			System.out.println("PPT转换成图片 发生异常！");
			e.printStackTrace();
			throw new MessageException("PPT转换成图片失败！");
		}
	}

	*//**
	 * ppt转pdf
	 * 
	 * @param pptFile
	 * @param pdfFile
	 * @throws ConnectException
	 *//*
	public static void pptToPdf(File pptFile, File pdfFile) throws ConnectException {
		OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
		connection.connect();
		ConverterDocument converter = new ConverterDocument(connection);
		converter.convert(pptFile, pdfFile);
		connection.disconnect();
	}

	public static Integer pdfToIamge(float zoom, File pdfFile, File imgFile, String fileName) {
		Document document = null;
		int count = 0;
		try {
			document = new Document();
			document.setFile(pdfFile.getAbsoluteFile().getAbsolutePath());
			float rotation = 0;
			int maxPages = document.getPageTree().getNumberOfPages();
			for (int i = 0; i < maxPages; i++) {
				BufferedImage bfimage = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
						Page.BOUNDARY_CROPBOX, rotation, zoom);

				bfimage = setGraphics(bfimage);

				RenderedImage rendImage = bfimage;
				// 图片将要存放的路径
				String absolutePath = imgFile.getAbsolutePath() + "/" + fileName + "_" + (i + 1) + ".jpeg";
				ImageIO.write(rendImage, "jpeg", new File(absolutePath));
				bfimage.flush();
				count += 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document != null) {
				document.dispose();
			}
		}

		return count;
	}
	
	public static BufferedImage setGraphics(BufferedImage bfimage) {
		Graphics2D g = bfimage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		// // 5、设置水印文字颜色
		// g.setColor(color);
		// // 6、设置水印文字Font
		// g.setFont(font);
		// // 7、设置水印文字透明度
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		// 设置旋转
		g.rotate(-Math.PI / 6);
		// g.drawString("落花雨科技", 0, (bfimage.getHeight() / 2) * 1);
		// 9、释放资源
		g.dispose();
		return bfimage;
	}*/
}
