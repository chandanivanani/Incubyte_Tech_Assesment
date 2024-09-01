package bookTest;

import java.time.Year;
import java.util.List;
import java.util.ArrayList;

import assesment.incubyte.book.Book;

public class DemoTestBookObjectStore {
    static private List<Book> bookObjectList = new ArrayList<>();

    static { 
        bookObjectList.add(new Book("9780262035613", "Introduction to Algorithms", "Thomas H. Cormen", Year.of(2009)));
        bookObjectList.add(new Book("9780131103627", "The C Programming Language", "Brian W. Kernighan", Year.of(1988)));
        bookObjectList.add(new Book("9780201633610", "Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma", Year.of(1994)));
        bookObjectList.add(new Book("9780132350884", "Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin", Year.of(2008)));
        bookObjectList.add(new Book("9780201485677", "Refactoring: Improving the Design of Existing Code", "Martin Fowler", Year.of(1999)));
        bookObjectList.add(new Book("9780134685991", "Effective Java", "Joshua Bloch", Year.of(2018)));
        bookObjectList.add(new Book("9780137903955", "Artificial Intelligence: A Modern Approach", "Stuart Russell", Year.of(2020)));
        bookObjectList.add(new Book("9781449355739", "Fluent Python", "Luciano Ramalho", Year.of(2015)));
        bookObjectList.add(new Book("9780321751041", "Effective C++", "Scott Meyers", Year.of(2005)));
        bookObjectList.add(new Book("9781491954249", "Deep Learning with Python", "François Chollet", Year.of(2017)));
    }

    static public Book getBookObject(int index) {
        return bookObjectList.get(index);
    }
}
