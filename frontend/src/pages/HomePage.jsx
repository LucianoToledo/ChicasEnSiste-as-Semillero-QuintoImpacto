import Header from "../components/Header";
import Hero from "../components/Hero";
import Carousel from "../components/Carousel";
import Contact from "../components/Contact";
import Footer from "../components/Footer";
import iconoIntro from "../assets/images/icono-intro.png";
import iconoHtml from "../assets/images/icono-html.png";
import iconoUx from "../assets/images/icono-ux.png";

const hero = {
  title: "Bienvenidxs",
  secondText: "t is a long established fact that a reader will be",
  thirdText:
    "t is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
};

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

const HomePage = () => {
  return (
    <div className="2xl:container 2xl:mx-auto">
      <Header />
      <Hero
        title={hero.title}
        secondText={hero.secondText}
        thirdText={hero.thirdText}
      />
      <Carousel cards={cards} />
      <Contact />
      <Footer />
    </div>
  );
};

export default HomePage;
