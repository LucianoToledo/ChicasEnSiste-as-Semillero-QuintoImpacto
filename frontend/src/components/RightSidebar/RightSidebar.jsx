import "./RightSidebar.css";
import "tw-elements";

const RightSidebar = ({course, setVideo}) => {
  const currentSelect = (videoCourse) => {
    setVideo(videoCourse)    
  }

  return (
    <div
      className="w-60 h-full shadow-md bg-secondary rounded px-1 absolute"
      id="sidenavExample"
    >
      <ul className="relative">
        <li className="relative" id="sidenavEx1">
          {course.modules.map((module) => {
            
            return (
                <>
              <a
                className="flex items-center text-sm py-4 px-6 h-12 overflow-hidden text-white-700 text-ellipsis whitespace-nowrap rounded hover:text-white-900 hover:bg-gray-400 transition duration-300 ease-in-out cursor-pointer"
                data-mdb-ripple="true"
                data-mdb-ripple-color="dark"
                data-bs-toggle="collapse"
                data-bs-target="#collapseSidenavEx1"
                aria-expanded="true"
                aria-controls="collapseSidenavEx1"
                key={module.id}
              >
                <svg
                  aria-hidden="true"
                  focusable="false"
                  data-prefix="fas"
                  className="w-3 h-3 mr-3"
                  role="img"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 512 512"
                >
                  <path
                    fill="currentColor"
                    d="M216 0h80c13.3 0 24 10.7 24 24v168h87.7c17.8 0 26.7 21.5 14.1 34.1L269.7 378.3c-7.5 7.5-19.8 7.5-27.3 0L90.1 226.1c-12.6-12.6-3.7-34.1 14.1-34.1H192V24c0-13.3 10.7-24 24-24zm296 376v112c0 13.3-10.7 24-24 24H24c-13.3 0-24-10.7-24-24V376c0-13.3 10.7-24 24-24h146.7l49 49c20.1 20.1 52.5 20.1 72.6 0l49-49H488c13.3 0 24 10.7 24 24zm-124 88c0-11-9-20-20-20s-20 9-20 20 9 20 20 20 20-9 20-20zm64 0c0-11-9-20-20-20s-20 9-20 20 9 20 20 20 20-9 20-20z"
                  ></path>
                </svg>
                <span className="hover:text-white-900">{module.name}</span>
                <svg
                  aria-hidden="true"
                  focusable="false"
                  data-prefix="fas"
                  className="w-3 h-3 ml-auto"
                  role="img"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 448 512"
                >
                  <path
                    fill="currentColor"
                    d="M207.029 381.476L12.686 187.132c-9.373-9.373-9.373-24.569 0-33.941l22.667-22.667c9.357-9.357 24.522-9.375 33.901-.04L224 284.505l154.745-154.021c9.379-9.335 24.544-9.317 33.901.04l22.667 22.667c9.373 9.373 9.373 24.569 0 33.941L240.971 381.476c-9.373 9.372-24.569 9.372-33.942 0z"
                  ></path>
                </svg>
              </a>
              
                {module.videosCourse.map((videoCourse) =>{
                    return(
                      <ul className="relative accordion-collapse collapse" id="collapseSidenavEx1" aria-labelledby="sidenavEx1" data-bs-parent="#sidenavExample">
                        <li className="relative" key={videoCourse.id}>
                            <button onClick={() => {currentSelect(videoCourse)}} className="flex items-center text-xs py-4 pl-12 pr-6 h-6 overflow-hidden text-gray-100 text-ellipsis whitespace-nowrap rounded hover:text-gray-700 hover:bg-gray-100 transition duration-300 ease-in-out" data-mdb-ripple="true" data-mdb-ripple-color="dark">{videoCourse.name.length > 25 ?<> <h6 style={{ whiteSpace: "pre" }}> {videoCourse.name.substring(0,25)}</h6> <h6>{videoCourse.name.substring(26,50)}</h6> </>  : <h6>{videoCourse.name}</h6>}</button>
                        </li>
                      </ul>
                    )
                })}         
            </>
            );
          })}
        </li>
      </ul>
    </div>
  );
};

export default RightSidebar;
