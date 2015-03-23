package org.nextime.ion.common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.activation.FileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nextime.ion.backoffice.bean.ResourceXmlBean;
import org.nextime.ion.backoffice.bean.Resources;

public class ResourceServlet extends HttpServlet {

	public static String relativePath = null;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {

		String qs = request.getPathInfo().substring(1);

		String resourcesId = qs.substring(0, qs.indexOf("/"));
		String resourceId = qs.substring(qs.indexOf("/") + 1);

		// retrieve resources selected
		String path = null;
		try {
			ResourceXmlBean bean =
				Resources.getResourceXmlBean(this, resourcesId);
			path = bean.getDirectory();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		String realPath = getServletContext().getRealPath("/");
		File resources = new File(realPath, ResourceServlet.relativePath);
		File tresources = new File(resources, path);
		File tfile = new File(tresources, resourceId);

		if (request.getParameter("width") != null
			|| request.getParameter("height") != null) {

			int w = Integer.parseInt(request.getParameter("width") + "");
			int h = Integer.parseInt(request.getParameter("height") + "");

			
			File cache = new File(resources, "cache");
			File possibleCached =
				new File(cache, w + "_" + h + "_" + tfile.getName() + ".jpg");
			
			String mimeType =
				FileTypeMap.getDefaultFileTypeMap().getContentType(possibleCached);
			response.setContentType(mimeType);
			
			if (possibleCached.exists()) {
				// send content
				byte[] buffer = new byte[(int) possibleCached.length()];
				FileInputStream fis = new FileInputStream(possibleCached);
				fis.read(buffer);
				fis.close();
				response.getOutputStream().write(buffer);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} else {

				BufferedImage image = ImageIO.read(tfile);

				BufferedImage myImage =
					new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics g = myImage.getGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, w, h);

				int width = image.getWidth();
				int height = image.getHeight();

				if (width > height) {
					Image newImage =
						image.getScaledInstance(w, -1, Image.SCALE_SMOOTH);
					g.drawImage(
						newImage,
						0,
						(h - newImage.getHeight(null)) / 2,
						null);
				} else {
					Image newImage =
						image.getScaledInstance(-1, h, Image.SCALE_SMOOTH);
					g.drawImage(
						newImage,
						(w - newImage.getWidth(null)) / 2,
						0,
						null);
				}

				ImageIO.write(myImage, "jpg", possibleCached);
				ImageIO.write(myImage, "jpg", response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}

		} else {

			// retrieve mime type
			String mimeType =
				FileTypeMap.getDefaultFileTypeMap().getContentType(tfile);
			response.setContentType(mimeType);

			// send content
			byte[] buffer = new byte[(int) tfile.length()];
			FileInputStream fis = new FileInputStream(tfile);
			fis.read(buffer);
			fis.close();
			response.getOutputStream().write(buffer);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		}
	}

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		super.init();
		relativePath = getInitParameter("relativePath");
	}

}
