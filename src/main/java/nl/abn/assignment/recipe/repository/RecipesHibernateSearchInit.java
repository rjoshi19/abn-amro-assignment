package nl.abn.assignment.recipe.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RecipesHibernateSearchInit implements ApplicationListener<ContextRefreshedEvent> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        try {
            Search.session(entityManager).massIndexer().startAndWait();
        } catch (InterruptedException e) {
            System.out.println("Error occured trying to build Hibernate Search indexes "
                    + e);
        }
    }
}