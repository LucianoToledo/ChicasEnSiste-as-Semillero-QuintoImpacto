import React from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import Carousel from '../components/Carousel';
import iconoIntro from "../assets/images/icono-intro.png";
import iconoHtml from "../assets/images/icono-html.png";
import iconoUx from "../assets/images/icono-ux.png";

const cards = [
    {
      icon: iconoIntro,
      title: "Introducción a la programacion",
      description:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
      subtitle: "Lorem Ipsum",
      price: "$1000",
    },
    {
      icon: iconoHtml,
      title: "HTML5 y CSS3",
      description:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
      subtitle: "Lorem Ipsum",
      price: "$1000",
    },
    {
      icon: iconoUx,
      title: "Introducción a UX/UI",
      description:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
      subtitle: "Lorem Ipsum",
      price: "$1000",
    },
  ];

const CoursesPage = () => {
  return (
    <div className="2xl:container 2xl:mx-auto">
        <Header />
        <div className="text-primary font-bold text-center text-5xl lg:text-8xl pt-8">
            <p>Cursos</p>
        </div>
        <Carousel cards={cards} />
        <Footer />
      </div>
  )
}

export default CoursesPage