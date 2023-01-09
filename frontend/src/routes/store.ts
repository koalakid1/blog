import {writable} from "svelte/store";
import type {Board} from "./api/types.js";

export const boards = writable<Board[]>([]);