/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.business;

import flw.business.core.DoloresPlayer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "DoloresDS")
    private EntityManager em;

    /**
     *
     * Creates a new User
     *
     * @param email
     * @param password
     * @param role
     * @param unlocked
     * @return res is true if success else false
     */
    public boolean addNewUser(String email, String password, String role, boolean unlocked) {
        boolean res;
        try {
            // ggf +++ Hashing ausstellen +++
            
            //The password is hashed with SHA-512
            MessageDigest mda = MessageDigest.getInstance("SHA-512");
            byte[] hashPassword = mda.digest(password.getBytes());

            //convert the byte to hex format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hashPassword.length; i++) {
                sb.append(Integer.toString((hashPassword[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            DoloresPlayer newUser = new DoloresPlayer();
            newUser.setId(email); //the id is the email
            newUser.setPassword(password);
            newUser.setUnlocked(unlocked);
            
            if(email.equals("admin")){
                newUser.setUnlocked(true);
            }
            System.out.println("Datenbank" + em.toString());
            newUser.setRole(role);
            em.persist(newUser);
            res = true;
        } catch (NoSuchAlgorithmException e) {
            res = false;
        }
        return res;
    }

    /**
     * returns the player from DB (by String playername)
     *
     * @param playerName
     * @return
     */
    public DoloresPlayer retrieveCurrentPlayer(String playerName) {
        em.clear();
        em.flush();

        Query query = em.createQuery("SELECT d FROM DoloresPlayer d WHERE d.id='" + playerName + "'");
        Collection<DoloresPlayer> collection = (Collection<DoloresPlayer>) query.getResultList();
        return (DoloresPlayer) collection.toArray()[0];
    }
}