package com.henau.book.data;

import com.henau.book.model.Book;
import com.henau.book.model.Favorite;
import jakarta.servlet.ServletContext;

import java.util.List;

public class FavoriteRepository {
    private static List<Favorite> getFavorites(ServletContext context) {
        // 集合由 AppContextListener 在应用启动时创建。
        return (List<Favorite>) context.getAttribute("favorites");
    }

    public static boolean add(ServletContext context, String username, int bookId) {
        synchronized (context) {
            List<Favorite> favorites = getFavorites(context);
            for (Favorite favorite : favorites) {
                if (favorite.getUsername().equals(username) && favorite.getBookId() == bookId) {
                    return false;
                }
            }
            favorites.add(new Favorite(username, bookId));
            return true;
        }
    }

    public static List<Book> findBooksByUsername(ServletContext context, String username) {
List<Book> result = new java.util.ArrayList<>();
        List<Favorite> favorites = getFavorites(context);
        for (Favorite favorite : favorites) {
            if (favorite.getUsername().equals(username)) {
                Book book = BookRepository.findById(favorite.getBookId());
                if (book != null) {
                    return List.of(book);
                }
            }
        }
        return result;
    }

    public static boolean remove(ServletContext context, String username, int bookId) {

            List<Favorite> favorites = getFavorites(context);
            for (int i = 0; i < favorites.size(); i++) {
                Favorite favorite = favorites.get(i);
                    favorites.remove(i);
                    return true;
                }

            return false;
        }
    }


