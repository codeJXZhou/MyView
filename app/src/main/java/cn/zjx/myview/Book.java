package cn.zjx.myview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zjx on 2018/7/12.
 */
public class Book implements Parcelable{
    public String name;
    public int id;

    protected Book(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    public Book() {

    }
    public Book(int u,String s) {
        this.name=s;
        this.id=u;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(name);
        parcel.writeInt(id);
    }

    public void readFromParcel(Parcel in){
        name = in.readString();
        id = in.readInt();
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
