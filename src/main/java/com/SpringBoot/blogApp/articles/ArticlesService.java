package com.SpringBoot.blogApp.articles;

import com.SpringBoot.blogApp.articles.dtos.CreateArticleRequest;
import com.SpringBoot.blogApp.articles.dtos.UpdateArticleRequest;
import com.SpringBoot.blogApp.users.UsersRepository;
import com.SpringBoot.blogApp.users.UsersService;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;

    public ArticlesService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles(){
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug){
        var article=articlesRepository.findBySlug(slug);
        if (article==null){
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }
    public ArticleEntity createArticle(CreateArticleRequest articleRequest, Long authorId){
        var author =usersRepository.findById(authorId).orElseThrow(()-> new UsersService.userNotFoundException(authorId));
        return articlesRepository.save(ArticleEntity.builder()
                        .title(articleRequest.getTitle())
                        .slug(articleRequest.getTitle().toLowerCase().replace("\\s+","-"))
                        .body(articleRequest.getBody())
                       .subtitle(articleRequest.getSubTitle())
                        .author(author)
                        .build());
    }

    public ArticleEntity updateArticle(UpdateArticleRequest updateArticleRequest,Long articleId){
        ArticleEntity articleEntity=articlesRepository.findById(articleId).orElseThrow(()->new ArticleNotFoundException(articleId));
        if(updateArticleRequest.getTitle()!=null){
            articleEntity.setTitle(updateArticleRequest.getTitle());
            articleEntity.setSlug(updateArticleRequest.getTitle().toLowerCase().replace("\\s+","-"));
        }
        if(updateArticleRequest.getBody()!=null){
            articleEntity.setBody(updateArticleRequest.getBody());
        }
        if(updateArticleRequest.getSubTitle()!=null){
            articleEntity.setSubtitle(updateArticleRequest.getSubTitle());
        }
        return articlesRepository.save(articleEntity);
    }

    static class ArticleNotFoundException extends IllegalArgumentException{
        public ArticleNotFoundException(String slug){
            super("Article with slug: "+slug+ " not found");

        }
        public ArticleNotFoundException(Long articleId){
            super("Article with Id: "+articleId+ " not found");

        }

    }
}
