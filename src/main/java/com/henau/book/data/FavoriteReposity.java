package com.henau.book.data;

import com.henau.book.model.Favorite;
import jakarta.servlet.ServletContext;

import java.util.List;

public class FavoriteReposity  {
    private static List<Favorite> getFavorites(ServletContext context) {
        // 集合由 AppContextListener 在应用启动时创建。
        return (List<Favorite>) context.getAttribute("favorites");
    }

    public static boolean add(ServletContext context,String username,int bookId){
        synchronized (context){
            List<Favorite> favorites = getFavorites(context);
            for (Favorite favorite : favorites) {
                if (favorite.getUsername().equals(username) && favorite.getBookId() == bookId) {
                    return false;
                }
            }
            favorites.add(new Favorite(username,bookId));
            return true;
        }
    }
}
