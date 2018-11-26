// INewAidlInterface.aidl
package cn.zjx.myview;

// Declare any non-default types here with import statements
import cn.zjx.myview.Book;
interface INewAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
void onNewBook(in Book book);
}
