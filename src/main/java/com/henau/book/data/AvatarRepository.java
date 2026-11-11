package com.henau.book.data;

import jakarta.servlet.ServletContext;
import java.util.Map;

/**
 * 课程演示使用 ServletContext 内存集合保存头像。
 * Tomcat 重启后头像会丢失，不用于生产环境。
 */
public class AvatarRepository {
    public static class Avatar {
        private final byte[] bytes;
        private final String contentType;

        public Avatar(byte[] bytes, String contentType) {
            this.bytes = bytes.clone();
            this.contentType = contentType;
        }

        public byte[] getBytes() {
            return bytes.clone();
        }

        public String getContentType() {
            return contentType;
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Avatar> avatars(ServletContext context) {
        return (Map<String, Avatar>) context.getAttribute("avatars");
    }

    public static void save(ServletContext context, String username, Avatar avatar) {
        synchronized (context) {
            avatars(context).put(username, avatar);
        }
    }

    public static Avatar find(ServletContext context, String username) {
        synchronized (context) {
            return avatars(context).get(username);
        }
    }
}
