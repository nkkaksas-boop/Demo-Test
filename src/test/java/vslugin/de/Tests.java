package vslugin.de;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Tests {

    private UserFormValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserFormValidator();
    }


    private UserForm validForm() {
        UserForm f = new UserForm();
        f.setName("Иванов Иван Иванович");
        f.setBirthDate("2000-10-01");
        f.setEmail("ivan@mail.ru");
        f.setPhone("+7-999-888-77-66");
        f.setInn("123456789012");
        f.setPassport("4512345678");
        return f;
    }

    // TC001: валидные данные
    @Test
    void test01_allFieldsValid() {
        ValidationResult result = validator.validate(validForm());
        assertTrue(result.isValid(), "Ожидалось отсутствие ошибок: " + result.getErrors());
    }

    // TC002: ИНН начинается с 00 негативный
    @Test
    void test02_innStartsWith00() {
        UserForm f = validForm();
        f.setInn("004567891234");
        ValidationResult result = validator.validate(f);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("inn"));
    }

    // TC003: возраст < 16 (негативный)
    @Test
    void test03_ageUnder16() {
        UserForm f = validForm();
        f.setBirthDate("2015-01-01");
        ValidationResult result = validator.validate(f);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("birthDate"));
    }

    // TC004: неверный телефон (негативный)
    @Test
    void test04_wrongPhoneFormat() {
        UserForm f = validForm();
        f.setPhone("+79998887766"); // без дефисов
        ValidationResult result = validator.validate(f);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("phone"));
    }

    // TC005: граничное — имя ровно 40 символов
    @Test
    void test05_nameExactly40Chars() {
        UserForm f = validForm();
        f.setName("А".repeat(40));
        ValidationResult result = validator.validate(f);
        assertNull(result.getErrors().get("name"));
    }

    // TC006: граничное — имя 41 символ
    @Test
    void test06_name41Chars() {
        UserForm f = validForm();
        f.setName("А".repeat(41));
        ValidationResult result = validator.validate(f);
        assertNotNull(result.getErrors().get("name"));
    }

    // TC007: неверный email
    @Test
    void test07_wrongEmail() {
        UserForm f = validForm();
        f.setEmail("ivanmail.ru"); // нет @
        ValidationResult result = validator.validate(f);
        assertNotNull(result.getErrors().get("email"));
    }

    // TC008: серия паспорта 00
    @Test
    void test08_passportSeries00() {
        UserForm f = validForm();
        f.setPassport("0012345678");
        ValidationResult result = validator.validate(f);
        assertNotNull(result.getErrors().get("passport"));
    }
}