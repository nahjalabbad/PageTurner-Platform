package com.example.pageturner.Service;

import com.example.pageturner.Api.ApiException;
import com.example.pageturner.DTO.WriterDTO;
import com.example.pageturner.Model.*;
import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.BookRepository;
import com.example.pageturner.Repository.GenreRepository;
import com.example.pageturner.Repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WriterService {
    private final WriterRepository writerRepository;
    private final AuthRepository authRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public List<Writer> getAllWriters(){
        return writerRepository.findAll();
    }

    public void registerWriter(WriterDTO writerDTO){
        User user=new User();
        user.setRole("WRITER");
        user.setName(writerDTO.getName());
        user.setEmail(writerDTO.getEmail());
        user.setUsername(writerDTO.getUsername());
        user.setPhoneNumber(writerDTO.getPhoneNumber());
        user.setBio(writerDTO.getBio());

        String hashPassword = new BCryptPasswordEncoder().encode(writerDTO.getPassword());
        user.setPassword(hashPassword);

        Writer writer = new Writer();
        writer.setXAccount(writerDTO.getXAccount());
        writer.setInstagramAccount(writerDTO.getInstagramAccount());
        writer.setPersonalWebsite(writerDTO.getPersonalWebsite());


        user.setWriter(writer);
        writer.setUser(user);

        authRepository.save(user);
        writerRepository.save(writer);
    }

    public void updateWriter(Integer userId, WriterDTO writerDTO){
        User user=authRepository.findUserByUserId(userId);
        Writer writer=writerRepository.findWriterByWriterId(user.getUserId());

        user.setName(writerDTO.getName());
        user.setEmail(writerDTO.getEmail());
        user.setUsername(writerDTO.getUsername());
        user.setPhoneNumber(writerDTO.getPhoneNumber());
        user.setBio(writerDTO.getBio());

        String hashPassword=new BCryptPasswordEncoder().encode(writerDTO.getPassword());
        user.setPassword(hashPassword);

        writer.setXAccount(writerDTO.getXAccount());
        writer.setInstagramAccount(writerDTO.getInstagramAccount());
        writer.setPersonalWebsite(writerDTO.getPersonalWebsite());

        authRepository.save(user);
        writerRepository.save(writer);

    }

    public void deleteWriter(Integer adminId,Integer writerId){
        User user=authRepository.findUserByUserId(adminId);
        Writer writer=writerRepository.findWriterByWriterId(writerId);
        User user1=authRepository.findUserByUserId(writerId);

        writerRepository.delete(writer);

        authRepository.delete(user1);

    }


    //51 Reenad
    //Revised by Nahj
    public void follow(Integer writerId, Integer userIdToFollow) {
        Writer writer = writerRepository.findWriterByWriterId(writerId);
        User userToFollow = authRepository.findUserByUserId(userIdToFollow);

        if (writer == null) {
            throw new ApiException("writer not found!");
        } else if (userToFollow == null) {
            throw new ApiException("User to follow not found!");
        }

        if (writer.getFollowingUsers().contains(userToFollow)) {
            throw new ApiException("writer is already following the user!");
        }

        if(userToFollow.getFollowers()==null){
            userToFollow.setFollowers(0);
        }
        if(writer.getUser().getFollowing()==null){
            writer.getUser().setFollowing(0);
        }

        writer.getFollowingUsers().add(userToFollow);
        writer.getUser().setFollowing(writer.getUser().getFollowing()+1);
        writerRepository.save(writer);

        userToFollow.setFollowers(userToFollow.getFollowers() + 1);
        authRepository.save(userToFollow);
    }

    //52 Reenad
    //Revised by Nahj
    public void unfollow(Integer writerID, Integer userIdToUnfollow) {
        Writer writer = writerRepository.findWriterByWriterId(writerID);
        User userToUnfollow = authRepository.findUserByUserId(userIdToUnfollow);

        if (writer == null) {
            throw new ApiException("writer not found!");
        } else if (userToUnfollow == null) {
            throw new ApiException("User to unfollow not found!");
        }

        if (!writer.getFollowingUsers().contains(userToUnfollow)) {
            throw new ApiException("writer is not following the user!");
        }

        if(userToUnfollow.getFollowers()==null){
            userToUnfollow.setFollowers(0);
        }
        if(writer.getUser().getFollowing()==null){
            writer.getUser().setFollowing(0);
        }

        writer.getFollowingUsers().remove(userToUnfollow);
        writer.getUser().setFollowing(writer.getUser().getFollowing()-1);
        writerRepository.save(writer);

        userToUnfollow.setFollowers(userToUnfollow.getFollowers() - 1);
        authRepository.save(userToUnfollow);
    }


    //53 Reenad
    public Writer searchWriter(String name){
        User user = authRepository.findByName(name);
        if(user == null){
            throw new ApiException("no user found");
        }
        return user.getWriter();
    }

    //54 Reenad
    public String addPublishedGenreForWriter(Integer writerId, String genreTitle) {
        Writer writer = writerRepository.findWriterByWriterId(writerId);
        if (writer == null) {
            throw new ApiException("writer not found");
        }

        Genre genre = genreRepository.findGenreByTitle(genreTitle);
        if (genre == null) {
            throw new ApiException("Genre doesn't exist");
        }

        List<Genre> publishedGenre = writer.getPublishedGenre();
        if (publishedGenre == null) {
            publishedGenre = new ArrayList<>();
        }

        if (!publishedGenre.contains(genre)) {
            publishedGenre.add(genre);
            genre.getWriters().add(writer);
            genreRepository.save(genre);
            writer.setPublishedGenre(publishedGenre);
            writerRepository.save(writer);
            return "Published Genre added successfully.";
        }

        return "Cannot add genre.";

    }

}
