package com.example.demo;

import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.ProductsRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.entity.Categorie;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Products;
import com.example.demo.entity.Users;
import com.example.demo.service.CategoriesServiceImpl;
import com.example.demo.service.ProductsServiceImpl;
import com.example.demo.service.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ServiceTests {

    @InjectMocks
    private CategoriesServiceImpl categoriesService;

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private ProductsServiceImpl productsService;

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepository;


    @Test
    public void testFindCategoryById() {
        Categories category = new Categories(1L, Categorie.SMART, null);
        when(categoriesRepository.findById(1L)).thenReturn(Optional.of(category));

        Categories foundCategory = categoriesService.findById(1L);
        assertNotNull(foundCategory);
        assertEquals(Categorie.SMART, foundCategory.getName());
    }


    @Test
    public void testFindProductById() {
        Products product = new Products(1L, "Test Product", 100, 10, null);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));

        Products foundProduct = productsService.findById(1L);
        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
    }


    @Test
    public void testFindUserById() {
        Users user = new Users();
        user.setEmail("test@example.com");
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        Users foundUser = usersService.findById(1L);
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }
}
