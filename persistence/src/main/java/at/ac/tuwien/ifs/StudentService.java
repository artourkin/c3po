package at.ac.tuwien.ifs;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class StudentService {
    @Inject
    EntityManager em;

    @Transactional
    public void createGift(String giftDescription) {
        Student gift = new Student();
        gift.setFirstName("YOLO");
        em.persist(gift);
    }
}
