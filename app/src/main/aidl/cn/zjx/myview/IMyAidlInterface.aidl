// IMyAidlInterface.aidl
package cn.zjx.myview;

// Declare any non-default types here with import statements
import cn.zjx.myview.Book;
import cn.zjx.myview.INewAidlInterface;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
            List<Book> getBookList();
            void addBook(in Book book);
            void registerListener(INewAidlInterface face);
            void unregisterListener(INewAidlInterface face);
}
