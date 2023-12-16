package com.poc.happymocking.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompanyRecursionTest {
    @InjectMocks
    private Company company;
    @Test
    public void testRecursion() {
        company.populateChildren(0);
    }
}
