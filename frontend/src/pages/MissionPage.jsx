import React from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';

const MissionPage = () => {
  return (
    <div className="2xl:container 2xl:mx-auto">
      <Header />
      <div className="w-full bg-primary ">
        <div className="text-white font-bold text-center text-5xl lg:text-8xl pt-8 pb-6">
            <p>Misión</p>
        </div>
        <div className="text-white font-bold text-center lg:text-left text-2xl lg:text-6xl px-2 lg:px-16 py-8">
            <p>Why do we use it?</p>
        </div>
        <div className="text-white font-medium text-center lg:text-left text-xl lg:text-4xl px-2 lg:pl-16 lg:pr-24 py-8 lg:pb-44">
            <p>It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).</p>
        </div>
      </div>
      <Footer />
      </div>
  )
}

export default MissionPage;