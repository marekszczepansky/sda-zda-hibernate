package pl.sda.hibernate.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.hibernate.aop.LogEntry;
import pl.sda.hibernate.entity.NamedEntity;

import java.util.List;
import java.util.Set;

public abstract class RepositoryBaseDao<T extends NamedEntity> {
    protected final JpaRepository<T, Integer> jpaRepository;

    public RepositoryBaseDao(JpaRepository<T, Integer> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Transactional
    @LogEntry
    public void create(T entity) {
        jpaRepository.save(entity);
    }

    @Transactional
    @LogEntry
    public void create(Set<T> entities) {
        jpaRepository.saveAll(entities);
    }

    @LogEntry
    public T findById(int id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @LogEntry
    public List<T> getAll() {
        return jpaRepository.findAll();
    }
}
