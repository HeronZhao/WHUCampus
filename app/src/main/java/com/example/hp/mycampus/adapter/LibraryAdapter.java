package com.example.hp.mycampus.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.Book;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private List<Book> mbook;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView book_name;
        TextView book_author;
        TextView book_year;
        TextView book_branch;
        TextView book_shouldReturnDate;
        TextView book_returnDate;
        TextView book_fine;

        public ViewHolder(View view){
            super(view);
            book_name = (TextView) view.findViewById(R.id.book_title);
            book_author = (TextView)view.findViewById(R.id.book_author);
            book_year = (TextView)view.findViewById(R.id.book_year);
            book_branch = (TextView)view.findViewById(R.id.book_branch);
            book_shouldReturnDate = (TextView)view.findViewById(R.id.book_shouldReturnDate);
            book_returnDate = (TextView)view.findViewById(R.id.book_returnDate);
            book_fine=(TextView)view.findViewById(R.id.book_fine);
        }
    }

    public LibraryAdapter(ArrayList<Book> bookList){
        mbook = bookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mbook.get(position);
        holder.book_name.setText(book.getTitle());
        holder.book_author.setText(book.getAuthor());
        holder.book_year.setText(book.getYear());
        holder.book_branch.setText(book.getBranch());
        holder.book_shouldReturnDate.setText(book.getShouldReturnDate()+"   "+book.getShouldReturnTime());
        holder.book_returnDate.setText(book.getReturnDate()+"   "+book.getReturnTime());
        if(book.getFine().isEmpty()){
            holder.book_fine.setText(book.getFine());
        }else{
            holder.book_fine.setText(book.getFine()+"元");
        }
    }

    @Override
    public int getItemCount() {
        return mbook.size();
    }
}
