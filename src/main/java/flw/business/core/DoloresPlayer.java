/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author tilu
 */
@Entity
@Table(name = "t_user")
public class DoloresPlayer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "user_email")
    private String id;
    @Column(name = "user_pass")
    private String password;
    @Column(name = "date_inserted")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date insertDate;
    @Column(name = "date_updated")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(name = "date_last_login")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastLoginDate;
    @Column(name = "user_role")
    private String role;
    @Column(name = "seminar_id")
    private String seminarId;
    @Column(name = "freigeschaltet")
    private boolean unlocked;
   
    
//    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY) //CascadeType.REMOVE
//    private List<DoloresGameInfo> games;

    public DoloresPlayer(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public DoloresPlayer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public void addGame(DoloresGameInfo game) {
//        if (!games.contains(game)) {
//
//            games.add(game);
//        }
//    }
    
//    public void removeGame(DoloresGameInfo game){
//        if(games.contains(game)){
//            games.remove(game);
//        }
//    }
    
//    public void removeAllGames()
//    {
//        games.clear();
//    }

    
    //Soll gelöscht werden sobald Games entfernt wurde
//    public List<DoloresGameInfo> getGames() {
//        List<DoloresGameInfo> tmp_games = new ArrayList();
//        List<DoloresGameInfo> toRemove = new ArrayList();
//        for(DoloresGameInfo g : games)
//        {
//             if(g.getlDoloresStates().size()>0){
//                 tmp_games.add(g);
//             }else{
//                 toRemove.add(g);
//             }
//        }
//        for(DoloresGameInfo g : toRemove){
//            removeGame(g);
//        }
//        return tmp_games;
//    }

//    public void setGames(List<DoloresGameInfo> games) {
//        this.games = games;
//    }

    public String getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(String seminarId) {
        this.seminarId = seminarId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
    
    @PrePersist
    private void automaticDateInsert() {
        Date date = new Date();
        if (this.getInsertDate() == null) {
            this.setInsertDate(date);
        }
        this.setUpdateDate(date);
    }
}
