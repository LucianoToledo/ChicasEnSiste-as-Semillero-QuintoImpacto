import React from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import Contact from '../components/Contact';

const ContactPage = () => {
  return (
    <div className="2xl:container 2xl:mx-auto">
        <Header />
        <Contact />
        <Footer />
      </div>
  )
}

export default ContactPage