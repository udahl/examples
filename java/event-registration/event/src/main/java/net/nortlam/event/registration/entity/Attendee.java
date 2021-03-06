package net.nortlam.event.registration.entity;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import net.nortlam.event.registration.util.Encrypt;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
//@Entity(name="Attendee")
//@Table(name="ATTENDEE")
//        uniqueConstraints = 
//        @UniqueConstraint(name = "ATTENDEE_FIRST_AND_LAST_NAME",
//                                columnNames = {"FIRST_NAME", "LAST_NAME"}))
@XmlRootElement(name="Attendee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Attendee implements Serializable {

    private static final Logger LOG = Logger.getLogger(Attendee.class.getName());

    public static final String COLUMN_ID = "id";
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="ATTENDEE_ID")
    @XmlAttribute(name = COLUMN_ID, required=false)
    private long ID;
    
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final int LENGTH_FIRST_NAME = 40;
//    @Column(name="FIRST_NAME", length = LENGTH_FIRST_NAME, nullable = false)
    @XmlElement(name=COLUMN_FIRST_NAME, type=String.class, required=true)
    private String firstName;
    
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final int LENGTH_LAST_NAME = 40;
//    @Column(name="LAST_NAME", length = LENGTH_LAST_NAME, nullable = false)
    @XmlElement(name=COLUMN_LAST_NAME, type=String.class, required=true)
    private String lastName;

    public static final String COLUMN_EMAIL = "email";
    public static final int LENGTH_EMAIL = 120;
//    @Column(name="EMAIL", length = LENGTH_EMAIL, nullable = false, unique = true)
    @XmlElement(name=COLUMN_EMAIL, type=String.class, required=true)
    private String email;

    public static final String COLUMN_PASSWORD = "password";
    public static final int LENGTH_PASSWORD = 120;
//    @Column(name="PASSWORD", length = LENGTH_PASSWORD, nullable = false)
    @XmlElement(name="password", type=String.class, required=true)
    private String password;

    public Attendee() {
    }

    public Attendee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Attendee(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    
    public Attendee(String json) throws JsonException, JsonParsingException, IllegalStateException {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject object = reader.readObject();
        try {
            try {
                setID(object.getInt(COLUMN_ID));
            } catch(NullPointerException ex) {
                // if the specified name doesn't have any mapping
                setID(0);
            }
            
            try {
                setFirstName(object.getString(COLUMN_FIRST_NAME));
            } catch(NullPointerException ex) {
                LOG.log(Level.WARNING, "### NULL POINTER EXCEPTION: firstName is Missing");
                setFirstName(null);
            }
            
            try {
                setLastName(object.getString(COLUMN_LAST_NAME));
            } catch(NullPointerException ex) {
                LOG.log(Level.WARNING, "### NULL POINTER EXCEPTION: lastName is Missing");
                setLastName(null);
            }
            
            try {
                setEmail(object.getString(COLUMN_EMAIL));
            } catch(NullPointerException ex) {
                LOG.log(Level.WARNING, "### NULL POINTER EXCEPTION: email is Missing");
                setEmail(null);
            }
            
            try {
                setPassword(object.getString(COLUMN_PASSWORD));
            } catch(NullPointerException ex) {
                LOG.log(Level.WARNING, "### NULL POINTER EXCEPTION: password is Missing");
                setPassword(null);
            }
            
        } catch(ClassCastException ex) {
            //  if the value for specified name mapping is not assignable to JsonNumber
            LOG.log(Level.SEVERE, "### CLASS CAST EXCEPTION:{0}", ex.getMessage());
        }
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Encrypt.encrypt(password); // ENCRYPTION GOES HERE
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 97 * hash + Objects.hashCode(this.firstName);
        hash = 97 * hash + Objects.hashCode(this.lastName);
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attendee other = (Attendee) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("<Attendee ID=\"").append(ID).append("\">");
//        builder.append("<FirstName>").append(firstName != null ? firstName : "NULL")
//                .append("</FirstName>");
//        builder.append("<LastName>").append(lastName != null ? lastName : "NULL")
//                .append("</LastName>");
//        builder.append("<Email>").append(email != null ? email : "NULL")
//                .append("</Email>");
//        builder.append("<Password>").append(password != null ? "*****" : "NULL").append("</Password>");
//        builder.append("</Attendee>");
//        
//        return builder.toString();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        if(ID > 0) builder.add(COLUMN_ID, ID);
        builder.add(COLUMN_FIRST_NAME, firstName);
        builder.add(COLUMN_LAST_NAME, lastName);
        builder.add(COLUMN_EMAIL, email);
        builder.add(COLUMN_PASSWORD, password);
        
        return builder.build().toString();
    }
}
