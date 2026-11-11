package com.henau.book.data;

import com.henau.book.model.Book;
import com.henau.book.model.Favorite;
import jakarta.servlet.ServletContext;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRepository {

    @SuppressWarnings("unchecked")
    private static List<Favorite> getFavorites(ServletContext context) {
        return (List<Favorite>) context.getAttribute("favorites");
    }

    public static boolean add(ServletContext context,
                              String username, int bookId) {
        synchronized (context) {
            List<Favorite> favorites = getFavorites(context);
            for (Favorite favorite : favorites) {
                if (favorite.getUsername().equals(username)
                        && favorite.getBookId() == bookId) {
                    return false;
                }
            }
            favorites.add(new Favorite(username, bookId));
            return true;
        }
    }

    public static List<Book> findBooksByUsername(
            ServletContext context, String username) {

        synchronized (context) {
            List<Book> result = new ArrayList<>();

            for (Favorite favorite : getFavorites(context)) {
                if (favorite.getUsername().equals(username)) {
                    Book book = BookRepository.findById(
                            favorite.getBookId());

                    if (book != null) {
                        result.add(book);
                    }
                }
            }
            return result;
        }
    }

    public static boolean remove(ServletContext context,
                                 String username, int bookId) {
        synchronized (context) {
            List<Favorite> favorites = getFavorites(context);

            for (int i = 0; i < favorites.size(); i++) {
                Favorite favorite = favorites.get(i);

                if (favorite.getUsername().equals(username)
                        && favorite.getBookId() == bookId) {
                    favorites.remove(i);
                    return true;
                }
            }
            return false;
        }
    }
}