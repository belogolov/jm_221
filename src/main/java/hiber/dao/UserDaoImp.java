package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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
   public void addCar(Car car) {
      sessionFactory.getCurrentSession().save(car);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<hiber.model.User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String carName, int carSeries) {
      String sql = "FROM User u WHERE u.car.name =:carName AND u.car.series=:carSeries";
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(sql);
      query.setParameter("carName", carName);
      query.setParameter("carSeries", carSeries);
      query.setMaxResults(1);
      User user = null;
      try {
         user = query.getSingleResult();
      } catch (NoResultException e) {

      }
      return user;
   }

}
