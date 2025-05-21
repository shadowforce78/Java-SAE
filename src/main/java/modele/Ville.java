package modele;

public class Ville {
    private String nom;

    public Ville(String nom){
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        // Comparaison insensible à la casse pour une meilleure correspondance
        return normaliserNom(nom).equals(normaliserNom(ville.nom));
    }

    @Override
    public int hashCode() {
        return normaliserNom(nom).hashCode();
    }

    /**
     * Normalise un nom de ville pour les comparaisons
     * Supprime les accents, tirets, espaces et convertit en minuscules
     */
    private String normaliserNom(String nom) {
        if (nom == null) return "";
        String result = nom.toLowerCase();
        // Remplacer les caractères spéciaux
        result = result.replace('é', 'e')
                      .replace('è', 'e')
                      .replace('ê', 'e')
                      .replace('à', 'a')
                      .replace('â', 'a')
                      .replace('î', 'i')
                      .replace('ô', 'o')
                      .replace('ù', 'u')
                      .replace('ç', 'c')
                      .replace('-', '_')
                      .replace(' ', '_');
        return result;
    }
}
