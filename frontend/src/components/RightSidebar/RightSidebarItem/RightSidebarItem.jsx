import { useState } from "react"
import './RightSidebarItem.css'

export default function RightSidebarItem({item, index}){
    const [open, setOpen] = useState(false)

    
    if(item.childrens, index){
        return (
            <div className={open ? "sidebar-item open" : "sidebar-item"} key={index} >
                <div className="sidebar-title">
                    <span>
                        { item.icon && <i className={item.icon}></i> }
                        {item.title}    
                        
                    </span> 
                    <i className="bi-chevron-down toggle-btn" onClick={() => setOpen(!open)}></i>
                </div>
                <div className="sidebar-content">
                    { item.childrens.map((child, index) => <RightSidebarItem key={index} item={child} />) }
                </div>
            </div>
        )
    }else{
        return (
            <a href={item.path || "#"} className="sidebar-item plain">
                { item.icon && <i className={item.icon}></i> }
                {item.title}
            </a>
        )
    }
}