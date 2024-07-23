package ma.hotel.hotelproject.Beans;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numéro;
    private String type;
    private double prixParJour;
    private String disponibilité;

    private String image ;
    private String description;

    private double NombreDeLit;


    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    // Getters et setters
    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public double getNombreDeLit() {
        return NombreDeLit;
    }

    public void setNombreDeLit(double nombreDeLit) {
        NombreDeLit = nombreDeLit;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNuméro() {
        return numéro;
    }

    public void setNuméro(String numéro) {
        this.numéro = numéro;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getprixParJour() {
        return prixParJour;
    }

    public void setprixParJour(double prix) {
        this.prixParJour = prix;
    }

    public String isDisponibilité() {
        return disponibilité;
    }

    public void setDisponibilité(String disponibilité) {
        this.disponibilité = disponibilité;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}

