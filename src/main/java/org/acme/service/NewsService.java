package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.News;
import org.acme.repository.NewsRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class NewsService {

    @Inject
    NewsRepository newsRepository;

    @Transactional
    public List<News> getAllNews() {
        return newsRepository.list("order by publishedAt desc");
    }

    @Transactional
    public News getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    @Transactional
    public News createNews(News news) {
        if (news.getPublishedAt() == null) {
            news.setPublishedAt(LocalDateTime.now());
        }
        newsRepository.persist(news);
        return news;
    }

    @Transactional
    public News updateNews(Long id, News news) {
        News existing = newsRepository.findById(id);
        if (existing != null) {
            existing.setTitle(news.getTitle());
            existing.setContent(news.getContent());
            existing.setSummary(news.getSummary());
            existing.setImageUrl(news.getImageUrl());
            existing.setAuthor(news.getAuthor());
            existing.setCategory(news.getCategory());
            newsRepository.persist(existing);
        }
        return existing;
    }

    @Transactional
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
