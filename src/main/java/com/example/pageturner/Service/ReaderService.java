package com.example.pageturner.Service;

import com.example.pageturner.Api.ApiException;
import com.example.pageturner.DTO.ReaderDTO;
import com.example.pageturner.Model.Book;
import com.example.pageturner.Model.Genre;
import com.example.pageturner.Model.Reader;
import com.example.pageturner.Model.User;
import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.BookRepository;
import com.example.pageturner.Repository.GenreRepository;
import com.example.pageturner.Repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final AuthRepository authRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;


    public List<Reader> getAllReader(){
        return readerRepository.findAll();
    }

    public void registerReader(ReaderDTO readerDTO){
        User u=new User();

        u.setRole("READER");
        u.setName(readerDTO.getName());
        u.setUsername(readerDTO.getUsername());

        String hashpass=new BCryptPasswordEncoder().encode(readerDTO.getPassword());
        u.setPassword(hashpass);
        u.setEmail(readerDTO.getEmail());
        u.setPhoneNumber(readerDTO.getPhoneNumber());

        Reader r=new Reader();
        r.setUser(u);
        u.setReader(r);

        authRepository.save(u);
        readerRepository.save(r);
    }

    public void updateReader(Integer userId,ReaderDTO readerDTO){
        User u=authRepository.findUserByUserId(userId);
        Reader r=readerRepository.findReaderByReaderId(u.getUserId());

        u.setName(readerDTO.getName());
        u.setEmail(readerDTO.getEmail());
        u.setUsername(readerDTO.getUsername());
        u.setPhoneNumber(readerDTO.getPhoneNumber());
        u.setBio(readerDTO.getBio());

        String hashpass=new BCryptPasswordEncoder().encode(readerDTO.getPassword());
        u.setPassword(hashpass);


        authRepository.save(u);
        readerRepository.save(r);
    }

    public void deleteReader(Integer adminId,Integer readerId){
        User u=authRepository.findUserByUserId(adminId);
        Reader r=readerRepository.findReaderByReaderId(readerId);
        User u1=authRepository.findUserByUserId(readerId);

        readerRepository.delete(r);

        authRepository.delete(u1);
}

    //40 Reenad
    public String addFavoriteGenreForReader(Integer readerId, String genreTitle) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        if (reader == null) {
            throw new ApiException("Reader not found");
        }

        Genre genre = genreRepository.findGenreByTitle(genreTitle);
        if (genre == null) {
            throw new ApiException("Genre doesn't exist");
        }

        List<Genre> favoriteGenres = reader.getFavoriteGenre();
        if (favoriteGenres == null) {
            favoriteGenres = new ArrayList<>();
        }

        if (!favoriteGenres.contains(genre)) {
            favoriteGenres.add(genre);
            genre.getReaders().add(reader);
            genreRepository.save(genre);
            reader.setFavoriteGenre(favoriteGenres);
            readerRepository.save(reader);
            return "Genre added successfully.";
        }

        return "Cannot add genre.";
    }



    //41 Abdulrahman
    //Revised by Nahj and Reenad
    public void follow(Integer readerId, Integer userIdToFollow) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        User userToFollow = authRepository.findUserByUserId(userIdToFollow);

        if (reader == null) {
            throw new ApiException("Reader not found!");
        } else if (userToFollow == null) {
            throw new ApiException("User to follow not found!");
        }


        if (reader.getFollowingUsers().contains(userToFollow)) {
            throw new ApiException("Reader is already following the user!");
        }

        if(userToFollow.getFollowers()==null){
            userToFollow.setFollowers(0);
        }
        if(reader.getUser().getFollowing()==null){
            reader.getUser().setFollowing(0);
        }


        reader.getFollowingUsers().add(userToFollow);
        reader.getUser().setFollowing(reader.getUser().getFollowing()+1);
        readerRepository.save(reader);

        userToFollow.setFollowers(userToFollow.getFollowers() + 1);
        authRepository.save(userToFollow);
    }

    //42 Abdulrahman
    //Revised by Nahj and Reenad
    public void unfollow(Integer readerId, Integer userIdToUnfollow) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        User userToUnfollow = authRepository.findUserByUserId(userIdToUnfollow);

        if (reader == null) {
            throw new ApiException("Reader not found!");
        } else if (userToUnfollow == null) {
            throw new ApiException("User to unfollow not found!");
        }


        if (!reader.getFollowingUsers().contains(userToUnfollow)) {
            throw new ApiException("Reader is not following the user!");
        }

        if(userToUnfollow.getFollowers()==null){
            userToUnfollow.setFollowers(0);
        }
        if(reader.getUser().getFollowing()==null){
            reader.getUser().setFollowing(0);
        }

        reader.getFollowingUsers().remove(userToUnfollow);
        reader.getUser().setFollowing(reader.getUser().getFollowing()-1);
        readerRepository.save(reader);

        userToUnfollow.setFollowers(userToUnfollow.getFollowers() - 1);
        authRepository.save(userToUnfollow);
    }


    //43 Abdulrahman
    //Revised by Nahj and Reenad
    public void doneReading(Integer readerId, Integer bookId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        Book book = bookRepository.findBookByBookId(bookId);

        if (reader == null) {
            throw new ApiException("Reader not found!");
        } else if (book == null) {
            throw new ApiException("Book not found!");
        }

        for (Book doneBook : reader.getDoneReading()) {
            if (doneBook.getBookId().equals(bookId)) {
                throw new ApiException("You already marked this book as done reading");
            }
        }

        book.setStatus("finished");

        if (reader.getUser().getBookNumber() == null) {
            reader.getUser().setBookNumber(0);
        }
        reader.getUser().setBookNumber(reader.getUser().getBookNumber() + 1);
        reader.getReadingNow().remove(book);
        reader.getDoneReading().add(book);

        readerRepository.save(reader);
        bookRepository.save(book);
    }



    //44 Abdulrahman
    public void addToPurchasedBooks(Integer readerId, Integer bookId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        Book book = bookRepository.findBookByBookId(bookId);
        if (reader == null) {
            throw new ApiException("reader not found!");
        } else if (book == null) {
            throw new ApiException("book not found!");
        }
        reader.getPurchasedBooks().add(book);
        book.setStatus("not read");
        readerRepository.save(reader);
    }

    //45 Abdulrahman
    public void addToWantToRead(Integer readerId, Integer bookId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        Book book = bookRepository.findBookByBookId(bookId);
        if (reader == null) {
            throw new ApiException("reader not found!");
        } else if (book == null) {
            throw new ApiException("book not found!");
        }

        reader.getWantToRead().add(book);
        book.setStatus("want to read");
        readerRepository.save(reader);
        bookRepository.save(book);
    }

    //46 Abdulrahman
    public List<String> getPurchasedBooks(Integer readerId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        if (reader == null) {
            throw new ApiException("reader not found!");
        }
        return reader.getPurchasedBooks().stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }


    //47 Abdulrahman
    public List<String> getWantToRead(Integer readerId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        if (reader == null) {
            throw new ApiException("reader not found!");
        }
        return reader.getWantToRead().stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    //48 Abdulrahman
    public List<String> getDoneReading(Integer readerId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        if (reader == null) {
            throw new ApiException("reader not found!");
        }
        return reader.getDoneReading().stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }


    //49 Abdulrahman
    public void startReading(Integer readerId, Integer bookId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        Book book = bookRepository.findBookByBookId(bookId);
        if (reader == null) {
            throw new ApiException("reader not found!");
        } else if (book == null) {
            throw new ApiException("book not found!");
        }
        reader.getWantToRead().remove(book);
        reader.getReadingNow().add(book);
        book.setStatus("reading now");
        readerRepository.save(reader);
        bookRepository.save(book);
    }


    //50 Abdulrahman
    public List<String> getAllBooksReadingNow(Integer readerId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        if (reader == null) {
            throw new ApiException("reader not found!");
        }
        List<String> readingNow = new ArrayList<>();
        for (Book book : reader.getReadingNow()) {
            readingNow.add(book.getTitle());
        }
        return readingNow;
    }

    //========================================================================================




}
