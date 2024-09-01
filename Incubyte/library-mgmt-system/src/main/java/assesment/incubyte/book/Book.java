package assesment.incubyte.book;

import java.time.Year;

public class Book {
    private String ISBN;
    private String bookTitle;
    private String authorName;
    Year publicationYear;

    public Book() {
        throw new UnsupportedOperationException("Book Can't be Created Without Initial Data");
    }

    public Book(String ISBN, String bookTitle, String authorName, Year publicationYear) {
        checkParameterValididty_andRaiseAppropriateError(ISBN, bookTitle, authorName, publicationYear);
        this.ISBN = ISBN;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.publicationYear = publicationYear;
    }

    private void checkParameterValididty_andRaiseAppropriateError(String ISBN, String bookTitle, String authorName,
            Year publicationYear) {
        if (ISBN == null){
            throw new IllegalArgumentException("ISBN must not be null");
        }
        else if( ISBN.length() < 10){
            throw new IllegalArgumentException("ISBN must be of atleast 10 chracters long { " + ISBN + " }");

        }else if(ISBN.length() > 13) {
            throw new IllegalArgumentException("ISBN length can be max of 13 chracters long { " + ISBN + " }");
            
        }else{
            if (bookTitle == null || bookTitle.length() < 4) {
                throw new IllegalArgumentException("Book Title must contain atleast 4 chracters { " + bookTitle + " }");
            } else {
                if (authorName == null || authorName.length() < 4) {
                    throw new IllegalArgumentException(
                            "Author Name must contain atleast 4 chracters { " + authorName + " }");
                } else {
                    if (publicationYear == null) {
                        throw new IllegalArgumentException("Publication Year must not be Empty ");
                    } else if (publicationYear.equals(Year.of(0000))) {
                        throw new IllegalArgumentException("Publication Year must not be 0000 ");

                    } else if (publicationYear.isAfter(Year.now())) {
                        throw new IllegalArgumentException(
                                "Publication Year must not be greater than current ( " + Year.now() + " ) year");
                    }
                }
            }
        }
    }

    public Book(Object arg1, Object arg2, Object arg3, Object arg4, Object... args) {
        throw new UnsupportedOperationException(
                "Constructor should be called with only four(ie: bookname, title, authorName, publicationYear) arguments");
    }

    public String getISBN() {
        return this.ISBN;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public Year getPublicationYear() {
        return this.publicationYear;
    }

    @Override
    public int hashCode() {
        return ISBN.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        return ISBN.equals(other.ISBN);
    }

    @Override
    public String toString() {
        return "Book [ISBN=" + ISBN + ", bookTitle=" + bookTitle + ", authorName=" + authorName
                + ", publicationYear=" + publicationYear + "]";
    }

}
