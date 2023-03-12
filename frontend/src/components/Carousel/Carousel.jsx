import { Swiper, SwiperSlide } from "swiper/react";
import Card from "../Card";
import "./Carousel.css";
import "swiper/css";
import "swiper/css/grid";

const Carousel = ({ cards }) => {
  return (
    <Swiper
      className="bg-secondary h-[920px] my-52 shadow-[0px_3px_6px_rgb(0,0,41)]"
      slidesPerView={3}
      breakpoints={{
        // when window width is >= 320px
        320: {
          slidesPerView: 1,
          spaceBetween: 20,
        },
        // when window width is >= 640px
        640: {
          slidesPerView: 1,
          spaceBetween: 40,
        },
        // when window width is >= 960px qHD
        960: {
          slidesPerView: 2,
          spaceBetween: 40,
        },
        // when window width is >= 1280px HD
        1280: {
          slidesPerView: 3,
          spaceBetween: 20,
        },
        // when window width is >= 1920px FHD
        1920: {
          slidesPerView: 3,
          spaceBetween: -50,
        },
      }}
    >
      {cards.map((card, index) => {
        return (
          <SwiperSlide key={index} className="flex flex-row justify-center flex-wrap content-center">
            <Card
              iconImg={card.icon}
              title={card.title}
              description={card.description}
              subtitle={card.subtitle}
              price={card.price}
            />
          </SwiperSlide>
        );
      })}
    </Swiper>
  );
};

export default Carousel;
