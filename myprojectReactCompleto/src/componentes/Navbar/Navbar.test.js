// src/componentes/Navbar.test.js
import React from 'react';
// Asegúrate de que el path sea correcto para importar tu Navbar
import { Navbar } from './Navbar.jsx'; 
import { render, screen } from '@testing-library/react'; 
import { BrowserRouter } from 'react-router-dom'; 

// Suite de Pruebas Jasmine para el Navbar (IE2.2.1)
describe('Componente: Navbar', () => {

  // Utilidad para renderizar Navbar con el Router
  const renderNavbar = () => {
    // Es crucial envolver el Navbar en BrowserRouter, ya que utiliza <Link>
    return render(
      <BrowserRouter> 
        <Navbar />
      </BrowserRouter>
    );
  };

  // IE2.2.1: Pruebas de Renderizado (Renderizado Correcto / Props)
  it('debería renderizar el enlace "Home" correctamente', () => {
    renderNavbar(); 
    
    // Usamos RTL para buscar el texto 'Home' (ignorando mayúsculas)
    const homeLink = screen.getByText(/Home/i); 
    
    // Verificamos que el elemento se encontró
    expect(homeLink).toBeTruthy(); 
  });
  
  it('debería renderizar el enlace "Inventario"', () => {
    renderNavbar(); 
    
    // Buscamos el texto 'Inventario'
    const inventoryLink = screen.getByText(/Inventario/i); 
    
    expect(inventoryLink).toBeTruthy();
  });
  
  // IE2.2.1: Ejemplo de prueba de estructura (Componentes)
  it('debería contener el texto de marca "Mi Página"', () => {
    renderNavbar(); 
    const brandText = screen.getByText(/Mi Página/i); 
    expect(brandText).toBeTruthy();
  });
});