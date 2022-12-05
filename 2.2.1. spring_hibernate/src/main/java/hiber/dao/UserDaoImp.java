package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCarsModelAndSeries(String model, int series) {
      try {
         TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User u where u.car.model = :carModel and u.car.series = :carSeries");
         query.setParameter("carModel", model);
         query.setParameter("carSeries", series);
         return query.getSingleResult();
      }catch(Exception ex) {
         throw new IllegalArgumentException("User with this car's model and car's series doesn't exist", ex);
      }
   }
}
