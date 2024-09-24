package com.example.demo;

import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.ProductsRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.entity.Categorie;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Products;
import com.example.demo.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void testCategoriesRepository() {
        // Arrange
        Categories category = new Categories();
        category.setName(Categorie.SMART);
        entityManager.persist(category);
        entityManager.flush();

        // Act
        Optional<Categories> foundCategory = categoriesRepository.findById(category.getId());

        // Assert
        assertTrue(foundCategory.isPresent());
        assertEquals(Categorie.SMART, foundCategory.get().getName());
    }

    @Test
    void testProductsRepository() {
        // Arrange
        Products product = new Products();
        product.setName("Test Product");
        product.setPrice(10);
        product.setStock(100);
        entityManager.persist(product);
        entityManager.flush();

        // Act
        List<Products> allProducts = productsRepository.findAll();

        // Assert
        assertFalse(allProducts.isEmpty());
        assertTrue(allProducts.stream().anyMatch(p -> p.getName().equals("Test Product")));
    }

    @Test
    void testUsersRepository() {
        // Arrange
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setUserName("TestUser");
        user.setFullName("Test User");
        user.setPhoneNumber("1234567890");
        user.setAdress("123 Test Street, Test City, 12345"); // Adres eklendi
        entityManager.persist(user);
        entityManager.flush();

        // Act
        Optional<Users> foundUser = usersRepository.findById(user.getId());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
        assertEquals("Test User", foundUser.get().getFullName());
        assertEquals("1234567890", foundUser.get().getPhoneNumber());
        assertEquals("123 Test Street, Test City, 12345", foundUser.get().getAdress());
        assertEquals("TestUser", foundUser.get().getUsername());
        assertEquals("password", foundUser.get().getPassword());
    }
}