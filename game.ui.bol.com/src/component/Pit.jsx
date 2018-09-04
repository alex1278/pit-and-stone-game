import React from 'react';

import Stone from './Stone';

import './Pit.css'

const Pit = ({id, stones, type}) => (
    <div className={"pit flex-row " + type.toLowerCase()}>
        { stones.map(stone => <Stone key={stone} id={stone} />)}
    </div>
)

export default Pit;