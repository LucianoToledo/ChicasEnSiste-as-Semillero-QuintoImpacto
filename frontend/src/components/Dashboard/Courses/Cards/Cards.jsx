import React from 'react'
import Card from '../Card';

const Cards = ({cards}) => {
  
  return (
    <div>
      {cards.map((card, index) => {
      return(<Card key={index}
          icon={card.icon}
          title={card.nameCourse}
          description={card.description}
          price={card.price}
          id={card.id}
          />)
        })}
     </div>
  )
}

export default Cards