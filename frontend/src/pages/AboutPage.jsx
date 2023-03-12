import React from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';

const AboutPage = () => {
  return (
    <div className="2xl:container 2xl:mx-auto">
      <Header />
      <div className="w-full bg-primary ">
        <div className="text-white font-bold text-center text-5xl lg:text-8xl pt-8 pb-6">
            <p>Nosotras</p>
        </div>
        <div className="text-white font-bold text-center lg:text-left text-2xl lg:text-6xl px-2 lg:px-16 py-8">
            <p>What is Lorem Ipsum?</p>
        </div>
        <div className="text-white font-medium text-center lg:text-left text-xl lg:text-4xl px-2 lg:pl-16 lg:pr-24 py-8 lg:pb-44">
            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
        </div>
      </div>
      <Footer />
      </div>
  )
}

export default AboutPage