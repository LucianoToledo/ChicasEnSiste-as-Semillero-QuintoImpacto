import React, { useState } from "react";

const Auth = ({ children }) => {
  const [activeTab, setActiveTab] = useState(children[1].props.label);

  const handleClick = (e, newActiveTab) => {
    e.preventDefault();
    setActiveTab(newActiveTab);
  };

  return (
    <div className="flex flex-col bg-primary max-h-[758px]">
      {children.map(one => {
        if (one.props.label === activeTab) {
          return (
            <div key={one.props.label} className="h-full p-0 animate-inRight">
              {one.props.children}
            </div>
          );
        }
        return null;
      })}
      <ul className="flex items-center bg-white h-[84px] border-l-primary rounded-br-md list-none p-0 m-0 ">
        {children.map(tab => {
          const label = tab.props.label;
          return (
            <li className="flex-grow h-full p-0 m-0" key={label}>
              <button onClick={e => handleClick(e, label)} className={(label === activeTab ? "!text-white !bg-primary " : "") + "flex justify-center items-center no-underline h-full w-full font-bold text-2xl bg-white text-primary rounded-br-md hover:bg-primary hover:text-white hover:opacity-80"}>
                {label}
              </button>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default Auth;
