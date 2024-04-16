import React from "react";

export function DocSources(){

    let sources = [
        {
            id: 1,
            name: "local source",
            type: "local"
        },
        {
            id: 2,
            name: "google docs",
            type: "google docs"
        },
        {
            id: 3,
            name: "drop box",
            type: "drop box"
        }
    ]

    return <div>
        <p>This is doc sources page</p>
        <form>
            <p>name:<input type="text"></input></p>
            <p>path:<input type="text"></input></p>
            <button type="button">Add</button>
        </form>
        <ul>{sources.map(source => <li>
                {source.name}
                <button type="button">Delete</button>
            </li>)
        }</ul>
</div>
}

function map(list: number[], fn: any){

    let result = [];

    for(let i = 0; i < list.length; i++){
        result.push(fn(list[i]));
    }
    return result;
}