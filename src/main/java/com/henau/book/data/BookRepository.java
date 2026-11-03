package com.henau.book.data;

import com.henau.book.model.Book;

import java.util.List;

public class BookRepository {

    private static final List<Book> BOOKS = List.of(
            new Book(
                    1, "活着", "余华",
                    "讲述福贵一生的故事。", true
            ),
            new Book(
                    2, "朝花夕拾", "鲁迅",
                    "一部回忆性散文集。", false
            ),
            new Book(
                    3, "骆驼祥子", "老舍",
                    "讲述人力车夫祥子的故事。", true
            )
    );

    // 返回全部图书
    public static List<Book> findAll() {
        return BOOKS;
    }

    // 根据 ID 查找一本图书
    public static Book findById(int id) {
        for (Book book : BOOKS) {
            if (book.getId() == id) {
                return book;
            }
        }

        // 遍历结束仍未找到
        return null;
    }
}