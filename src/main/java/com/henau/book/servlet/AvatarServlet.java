package com.henau.book.servlet;

import com.henau.book.data.AvatarRepository;
import com.henau.book.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

@WebServlet(urlPatterns = {"/avatar/upload", "/avatar/image"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024, maxRequestSize = 2200000)
public class AvatarServlet extends HttpServlet {
    private static final long MAX_BYTES = 2L * 1024 * 1024;

    private User currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (User) session.getAttribute("loginUser");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = currentUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!"/avatar/image".equals(request.getServletPath())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        AvatarRepository.Avatar avatar =
                AvatarRepository.find(getServletContext(), user.getUsername());
        if (avatar == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] bytes = avatar.getBytes();
        response.setContentType(avatar.getContentType());
        response.setContentLength(bytes.length);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.getOutputStream().write(bytes);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        User user = currentUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!"/avatar/upload".equals(request.getServletPath())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        try {
            Part part = request.getPart("avatar");
            if (part == null || part.getSize() == 0) {
                redirectResult(request, response, "empty");
                return;
            }
            if (part.getSize() > MAX_BYTES) {
                redirectResult(request, response, "size");
                return;
            }

            String filename = part.getSubmittedFileName();
            String name = filename == null ? "" : filename.toLowerCase(Locale.ROOT);
            String type = part.getContentType();
            boolean jpg = (name.endsWith(".jpg") || name.endsWith(".jpeg"))
                    && "image/jpeg".equalsIgnoreCase(type);
            boolean png = name.endsWith(".png")
                    && "image/png".equalsIgnoreCase(type);
            if (!jpg && !png) {
                redirectResult(request, response, "type");
                return;
            }

            byte[] bytes = part.getInputStream().readAllBytes();
            if (bytes.length == 0 || bytes.length > MAX_BYTES ||
                    !isValidImage(bytes, png ? "png" : "JPEG")) {
                redirectResult(request, response, "invalid");
                return;
            }

            AvatarRepository.save(getServletContext(), user.getUsername(),
                    new AvatarRepository.Avatar(bytes, png ? "image/png" : "image/jpeg"));
            redirectResult(request, response, "success");
        } catch (IllegalStateException e) {
            // Tomcat 在请求超出 @MultipartConfig 限制时可能抛出此异常。
            redirectResult(request, response, "size");
        } catch (ServletException e) {
            redirectResult(request, response, "invalid");
        }
    }

    private void redirectResult(HttpServletRequest request,
                                HttpServletResponse response, String result)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/profile?avatar=" + result);
    }

    // 不只检查文件名或 Content-Type，还确认真实图像格式和像素尺寸。
    private boolean isValidImage(byte[] bytes, String expectedFormat) {
        try (ImageInputStream stream = ImageIO.createImageInputStream(
                new ByteArrayInputStream(bytes))) {
            if (stream == null) return false;
            Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
            if (!readers.hasNext()) return false;
            ImageReader reader = readers.next();
            try {
                reader.setInput(stream);
                String format = reader.getFormatName();
                if (!expectedFormat.equalsIgnoreCase(format)) return false;
                int width = reader.getWidth(0);
                int height = reader.getHeight(0);
                return width > 0 && height > 0 && width <= 4096 && height <= 4096;
            } finally {
                reader.dispose();
            }
        } catch (IOException | RuntimeException e) {
            return false;
        }
    }
}
