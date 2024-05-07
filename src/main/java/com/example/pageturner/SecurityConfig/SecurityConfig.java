package com.example.pageturner.SecurityConfig;

import com.example.pageturner.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                //ADMIN
                .requestMatchers("/api/v1/auth/get-all","/api/v1/genre/add","/api/v1/genre/get-all","/api/v1/genre/delete/{genreId}","/api/v1/genre/update/{genreId}",
                        "/api/v1/goals/get-all","/api/v1/meeting/get-all","/api/v1/event/get-all","/api/v1/post/get-all",
                        "/api/v1/reader/get-all","/api/v1/writer/get-all","/api/v1/reader/delete/{readerId}","/api/v1/writer/delete/{writerId}").hasAuthority("ADMIN")

                //ALL-Not signed
                .requestMatchers("/api/v1/auth/login","/api/v1/auth/logout","/api/v1/writer/add","/api/v1/reader/add",
                        "/api/v1/book/get-all","/api/v1/book/get-genre/{genre}","/api/v1/book/get-byauthor/{author}",
                        "/api/v1/book/get-alltime","/api/v1/book/get-language/{language}","/api/v1/book/search-name/{bookName}",
                        "/api/v1/book/get-byauthor/{author}","/api/v1/genre/get-books-by-genre/{genreTitle}",
                        "/api/v1/writer/search-writer/{name}").permitAll()

                //ALL-signed
                .requestMatchers("/api/v1/event/get-upcoming","/api/v1/event/search-name/{bookName}","/api/v1/event/search-title/{title}",
                        "/api/v1/meeting/get-all-upcoming-meetings","/api/v1/meeting/search-meeting-by-id/{meetingId}",
                        "/api/v1/meeting/search-meeting-by-title/{title}","/api/v1/meeting/search-meeting-by-book-name/{bookName}",
                        "/api/v1/event/get-all-current","api/v1/meeting/get-current-meetings",
                        "/api/v1/reader/follow/{userId}","/api/v1/reader/unfollow/{userId}","/api/v1/writer/follow/{followedId}",
                        "/api/v1/writer/unfollow/{unfollowedId}").authenticated()
                //Reader
                .requestMatchers("/api/v1/event/add","/api/v1/event/update/{eventId}","/api/v1/event/delete/{eventId}",
                        "/api/v1/event/start/{eventId}","/api/v1/event/end/{eventId}","/api/v1/event/get-previous",
                        "/api/v1/event/get-reader-events","/api/v1/event/get-current-event{eventID}","/api/v1/goals/get-my-goals",
                        "/api/v1/goals/add","/api/v1/goals/update/{goalsId}","/api/v1/goals/delete/{goalsId}",
                        "/api/v1/goals/change-goal-status/{goalsId}","/api/v1/join/join-meeting/{meetingId}",
                        "/api/v1/join/join-event/{eventId}","/api/v1/join/leave-meeting/{meetingId}",
                        "/api/v1/join/leave-event/{eventId}","/api/v1/join/get-event-code/{eventId}",
                        "/api/v1/join/get-meeting-code/{meetingId}","/api/v1/post/add/{bookId}","/api/v1/post/update/{bookId}/{postId}",
                        "/api/v1/post/delete/{postId}","/api/v1/post/get-post","/api/v1/reader/update",
                        "/api/v1/reader/add-favorite/{genre}","/api/v1/reader/add-to-purchases/{bookId}","/api/v1/reader/get-purchased-books",
                        "/api/v1/reader/add-want-to-read/{bookId}","/api/v1/reader/get-want-to-read","/api/v1/reader/done-reading/{bookId}",
                        "/api/v1/reader/get-done-reading","/api/v1/reader/start-reading/{bookId}","/api/v1/reader/get-all-reading-now", "/api/v1/reader/get-all-previous-meetings-for-reader").hasAuthority("READER")

                //Writer
                .requestMatchers("/api/v1/book/add","/api/v1/book/update/{bookId}","/api/v1/book/delete/{bookId}",
                        "/api/v1/book/get-mybooks","/api/v1/book/get-post/{title}","/api/v1/meeting/add","/api/v1/meeting/get-all-my-meetings",
                        "/api/v1/meeting/update/{meetingId}","/api/v1/meeting/delete/{meetingId}","/api/v1/meeting/start-meeting/{meetingId}",
                        "/api/v1/meeting/end-meeting/{meetingId}","/api/v1/meeting/get-all-previous-meetings-for-writer",
                        "/api/v1/meeting/get-current-meeting","/api/v1/meeting/get-all-my-meetings",
                        "/api/v1/post/get-posts-by-book-title/{bookTitle}","/api/v1/post/get-top-rated-post/{bookId}",
                        "/api/v1/writer/update","/api/v1/writer/add-published-genre-for-writer/{genre}").hasAuthority("WRITER")



                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}