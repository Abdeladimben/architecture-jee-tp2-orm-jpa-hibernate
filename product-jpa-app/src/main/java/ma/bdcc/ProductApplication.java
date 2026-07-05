package ma.bdcc;

import ma.bdcc.entities.Product;
import ma.bdcc.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder().name("Ordinateur").price(4500.0).quantity(10).build());
            productRepository.save(Product.builder().name("Imprimante").price(1200.0).quantity(5).build());
            productRepository.save(Product.builder().name("Smartphone").price(3000.0).quantity(20).build());
            productRepository.save(Product.builder().name("Tablette").price(2500.0).quantity(8).build());

            System.out.println("=== TOUS LES PRODUITS ===");
            List<Product> products = productRepository.findAll();
            products.forEach(p -> System.out.println(p.getName() + " - " + p.getPrice() + "€ - Qty: " + p.getQuantity()));

            System.out.println("\n=== PRODUIT ID 1 ===");
            Product product = productRepository.findById(1L).orElse(null);
            System.out.println(product);

            System.out.println("\n=== RECHERCHE: 'Ord' ===");
            productRepository.findByNameContains("Ord").forEach(p -> System.out.println(p.getName()));

            System.out.println("\n=== PRIX > 2000 ===");
            productRepository.findByPriceGreaterThan(2000.0)
                .forEach(p -> System.out.println(p.getName() + " - " + p.getPrice() + "€"));

            System.out.println("\n=== MISE À JOUR PRODUIT ID 1 ===");
            Product p1 = productRepository.findById(1L).orElse(null);
            if (p1 != null) {
                p1.setPrice(5000.0);
                productRepository.save(p1);
                System.out.println("Nouveau prix: " + p1.getPrice() + "€");
            }

            System.out.println("\n=== SUPPRESSION PRODUIT ID 4 ===");
            productRepository.deleteById(4L);
            System.out.println("Produit 4 supprimé. Restants:");
            productRepository.findAll().forEach(p -> System.out.println(p.getId() + " - " + p.getName()));
        };
    }
}
