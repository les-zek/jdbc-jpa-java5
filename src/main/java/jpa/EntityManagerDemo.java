package jpa;

import entity.Demo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EntityManagerDemo {
    public static void main(String[] args) {
        EntityManager em = MyPersistence.JAVA5.getEntityManager();
        //wyszukiwanie jednego obiektu po id
        Demo demo = em.find(Demo.class, 1);
        System.out.println(demo);

        //Dodanie encji do bazy
        Demo newDemo = new Demo();
        newDemo.setName("TESTOWY");
        newDemo.setPoints(300);
        //modyfikacja bazy wymaga transakcji
        em.getTransaction().begin();
        em.persist(newDemo);
        em.getTransaction().commit();

        //Przykład zapytania z parametrami
        //zapytanie zwracające wszystkie obiekty o podanej nazwie i liczbie punktów
        Query query = em.createQuery(
                "select d from Demo d where d.name = :qname and d.points = :qpoints",
                Demo.class
        );
        query.setParameter("qname", "TESTOWY");
        query.setParameter("qpoints", 300);
        List<Demo> resultList = query.getResultList();
        for(Demo d: resultList){
            System.out.println(d);
        }

        //usuniecie obiektu z bazy
        em.getTransaction().begin();
        em.remove(demo);
        em.getTransaction().commit();
        //obiekt usunięty z bazy ale istniejący w pamieci
        System.out.println(demo);

        demo = em.find(Demo.class, 1);
        //obiekt pobrany z bazy po usunięciu
        System.out.println(demo);
        em.close();
    }
}
