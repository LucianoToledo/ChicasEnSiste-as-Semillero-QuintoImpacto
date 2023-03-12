import Progressbar from "./ProgressBar/Progressbar";
import avatar from "../../../../assets/images/avatar-default.png";
import editAvatar from "../../../../assets/images/edit-avatar.png";
import editWhite from "../../../../assets/images/edit-white.png";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";

export const SidebarHeader = ({ userData }) => {
  const [value, setValue] = useState(0);
  useEffect(() => {
    setValue(user.progress);
  }, [value]);

  // const handleAvatarEdit = () => {
  //   alert("Editar Avatar");
  // };

  const user = {
    name: userData.firstName + " " + userData.lastName,
    avatar: "",
    // avatar: "../img/avatar.jpg",
    progress: 60,
  };

  return (
    <div className="container flex flex-col justify-center items-center">
      <div className="group border-none w-40 h-40">
        <img
          src={user.avatar === "" ? avatar : user.avatar}
          alt="avatar"
          className="border-none w-full h-full rounded-[50%] object-cover object-center"
        />
        <Link
          to="profile"
          className="overflow-hidden opacity-0 bg-none border-none absolute right-4 top-24 transition-opacity group-hover:opacity-100"
        >
          <img src={editAvatar} alt="editAvatar" />
        </Link>
      </div>
      <div className="flex group text-3xl text-white text-center my-3">
        <p className="mx-2">{user.name}</p>
        <Link
          to="profile"
          className="pt-2 w-5 opacity-0 group-hover:opacity-100 transition-opacity"
        >
          <img src={editWhite} alt="editWhite" />
        </Link>
      </div>
      <Progressbar value={value} max={100} width={100} color={"#EA497B"} />
    </div>
  );
};
