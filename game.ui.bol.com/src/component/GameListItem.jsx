import React from 'react';
import { Link } from 'react-router-dom';

import './GameListItem.css'

const GameListItem = ({game, number}) => (
    <Link to={game.url} className="game-list-item flex-row content-spaced">
        <h3 className="thin">
            #{number} Game Room
        </h3>
        <div>
            {game.players.length}
        </div>
    </Link>
)

export default GameListItem;