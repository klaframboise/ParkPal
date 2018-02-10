using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;

namespace ParkPal.EmbeddedResources
{
    class ResourceLoader
    {
        /// <summary>
        /// Searches the embedded files for the given filename. Throws an exception if no or multiple files are matching.
        /// </summary>
        /// <param name="assembly"></param>
        /// <param name="fileName"></param>
        /// <returns>A stream for the embedded file</returns>
        public static Stream GetEmbeddedResourceStream(Assembly assembly, string fileName)
        {
            var resourceNames = assembly.GetManifestResourceNames();    //get list of available resources

            var resourcePaths = resourceNames
                .Where(x => x.EndsWith(fileName, StringComparison.CurrentCultureIgnoreCase))
                .ToArray(); // get all resources with given filename

            if (!resourcePaths.Any())   // throw exception if no resources match
            {
                throw new Exception(string.Format("Resource ending with {0} not found.", fileName));
            }

            if (resourcePaths.Count() > 1)  // throw exception if more than one resource matches
            {
                throw new Exception(string.Format("Multiple resources ending with {0} found: {1}{2}", fileName, Environment.NewLine, string.Join(Environment.NewLine, resourcePaths)));
            }

            return assembly.GetManifestResourceStream(resourcePaths.Single());
        }
    }
}
