package com.henau.book.data;

import com.henau.book.model.Book;

import java.util.List;

public class BookRepository {

    private static final List<Book> BOOKS = List.of(
            new Book(
                    1, "活着", "余华", "小说",
                    "讲述福贵一生的故事。", true
            ),
            new Book(
                    2, "朝花夕拾", "鲁迅", "散文",
                    "一部回忆性散文集。", false
            ),
            new Book(
                    3, "骆驼祥子", "老舍", "小说",
                    "讲述人力车夫祥子的悲惨命运。", true
            ),
            new Book(
                    4, "小王子", "圣埃克苏佩里", "童话",
                    "讲述小王子在各个星球间旅行的故事。", true
            ),
            new Book(
                    5, "Java核心技术", "Cay S. Horstmann", "计算机",
                    "Java编程语言的经典教程。", true
            ),
            new Book(
                    6, "深入理解计算机系统", "Randal E. Bryant", "计算机",
                    "系统级编程的经典教材。", true
            ),
            new Book(
                    7, "人类简史", "尤瓦尔·赫拉利", "历史",
                    "从认知革命到科学革命的人类发展史。", true
            ),
            new Book(
                    8, "苏菲的世界", "乔斯坦·贾德", "哲学",
                    "以小说形式讲述西方哲学史。", false
            ),
            new Book(
                    9, "三体", "刘慈欣", "科幻",
                    "讲述地球文明与三体文明的碰撞。", true
            ),
            new Book(
                    10, "史蒂夫·乔布斯传", "沃尔特·艾萨克森", "传记",
                    "苹果公司创始人乔布斯的传记。", true
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
