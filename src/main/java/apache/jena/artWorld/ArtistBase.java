/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package apache.jena.artWorld;

/**
 * <p>Base class for artist-ontology based examples. Declares
 * common namespaces and provides some basic utilities.</p>
 */
public abstract class ArtistBase extends Base
{
    /***********************************/
    /* Constants                       */
    /***********************************/
	//allowed values : ontology ** all ** movement ** museum ** default ** world ** artefact
	public static final String LOAD_NG = "all";

    public static final String ARTIST_SCHEMA = "http://www.art.org/ontology/person#";
    public static final String WORLD_SCHEMA = "http://www.art.org/ontology/world#";
    
    // named graphs
    public static final String graphURI = "http://www.art.org/";
    public static final String ng_ontology ="http://www.art.org/ontology";
    public static final String ng_world ="http://www.art.org/world";
    public static final String ng_person ="http://www.art.org/person";
    public static final String ng_movement ="http://www.art.org/movement";
    public static final String ng_museum ="http://www.art.org/museum";
    public static final String ng_artefact ="http://www.art.org/artefact";

    /***********************************/
    /* Static variables                */
    /***********************************/

    /***********************************/
    /* Instance variables              */
    /***********************************/

    /***********************************/
    /* Constructors                    */
    /***********************************/

    /***********************************/
    /* External signature methods      */
    /***********************************/

    /***********************************/
    /* Internal implementation methods */
    /***********************************/

    /***********************************/
    /* Inner class definitions         */
    /***********************************/

}

