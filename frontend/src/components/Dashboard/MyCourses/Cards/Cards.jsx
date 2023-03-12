import { Swiper, SwiperSlide } from "swiper/react";
import Card from "../Card";
import "swiper/css";
import "swiper/css/grid";
import React from "react";
// import cardsStyle from "./Cards.module.css";

const Cards = ({cards}) => {
  return (
    <Swiper
        className="h-screen"
        slidesPerView={3}
        breakpoints= {{
            320: {
                slidesPerView: 1,
                spaceBetween: 20,
            },
            640: {
                slidesPerView: 1,
                spaceBetween: 40,
            },
            960: {
                slidesPerView: 2,
                spaceBetween: 40,
            },
            1280: {
                slidesPerView: 3,
                spaceBetween: 20,
              },
            1920: {
                slidesPerView: 3,
                spaceBetween: -50,
              },
        }}
    >
        {cards.map((card, index) => {
            return (
                <SwiperSlide key={index}>
                    <Card 
                        icon={card.icon}
                        title={card.courseName}
                        id={card.id}
                        courseId={card.courseId}
                    />
                </SwiperSlide>
            );
        })}
    </Swiper>
  );
};

export default Cards;
